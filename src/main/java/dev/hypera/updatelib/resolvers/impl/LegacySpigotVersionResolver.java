/*
 * UpdateLib - A simple update checking library for Minecraft Plugins.
 * Copyright (c) 2021 Joshua Sing <joshua@hypera.dev>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package dev.hypera.updatelib.resolvers.impl;

import dev.hypera.updatelib.UpdateLib;
import dev.hypera.updatelib.exceptions.VersionResolveFailureException;
import dev.hypera.updatelib.resolvers.IVersionResolver;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.stream.Collectors;
import javax.net.ssl.HttpsURLConnection;
import org.jetbrains.annotations.NotNull;

/**
 * Spigot version resolver, uses the legacy SpigotMC API to get the latest version of a resource.
 *
 * @author Joshua Sing <joshua@hypera.dev>
 */
public class LegacySpigotVersionResolver implements IVersionResolver {

	private static final String URL_FORMAT = "https://api.spigotmc.org/legacy/update.php?resource=%s";

	/**
	 * Get the current version of a SpigotMC resource.
	 * @param updateLib {@link UpdateLib} instance.
	 * @param resourceId Resource identifier.
	 * @return Current distributed version.
	 * @throws VersionResolveFailureException if something went wrong while getting the version.
	 */
	@Override
	public @NotNull String getVersion(@NotNull UpdateLib updateLib, long resourceId) throws VersionResolveFailureException {
		try {
			URL url = new URL(String.format(URL_FORMAT, resourceId));
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
			connection.setConnectTimeout(updateLib.getTimeout());
			connection.setReadTimeout(updateLib.getTimeout());

			if (connection.getResponseCode() != 200) {
				throw new VersionResolveFailureException("SpigotMC's legacy API did not respond with a 200 status code.");
			}

			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String data = bufferedReader.lines().collect(Collectors.joining());
			bufferedReader.close();

			if (data.contains("Invalid")) {
				throw new VersionResolveFailureException("SpigotMC's legacy API responded with an invalid resource error.");
			}

			return data;
		} catch (Exception ex) {
			throw new VersionResolveFailureException(ex);
		}
	}

}
