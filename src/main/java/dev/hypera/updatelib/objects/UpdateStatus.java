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

package dev.hypera.updatelib.objects;

import dev.hypera.updatelib.objects.enums.Status;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Update status
 *
 * @author Joshua Sing <joshua@hypera.dev>
 */
public class UpdateStatus {

	public static final UpdateStatus DEFAULT = new UpdateStatus(null, null, Status.UNAVAILABLE);

	private final String currentVersion;
	private final String distributedVersion;
	private final Status status;

	public UpdateStatus(@Nullable String currentVersion, @Nullable String distributedVersion, @NotNull Status status) {
		this.currentVersion = currentVersion;
		this.distributedVersion = distributedVersion;
		this.status = status;
	}

	public @Nullable String getCurrentVersion() {
		return currentVersion;
	}

	public @Nullable String getDistributedVersion() {
		return distributedVersion;
	}

	public @NotNull Status getStatus() {
		return status;
	}

	public boolean isAvailable() {
		return status.isAvailable();
	}

}
