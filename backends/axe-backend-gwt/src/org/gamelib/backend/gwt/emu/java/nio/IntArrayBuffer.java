/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package java.nio;

/** IntArrayBuffer, ReadWriteIntArrayBuffer and ReadOnlyIntArrayBuffer compose the implementation of array based int buffers.
 * <p>
 * IntArrayBuffer implements all the shared readonly methods and is extended by the other two classes.
 * </p>
 * <p>
 * All methods are marked final for runtime performance.
 * </p> */
abstract class IntArrayBuffer extends IntBuffer {

	protected final int[] backingArray;

	protected final int offset;

	IntArrayBuffer (int[] array) {
		this(array.length, array, 0);
	}

	IntArrayBuffer (int capacity) {
		this(capacity, new int[capacity], 0);
	}

	IntArrayBuffer (int capacity, int[] backingArray, int offset) {
		super(capacity);
		this.backingArray = backingArray;
		this.offset = offset;
	}

	public final int get () {
		if (position == limit) {
			throw new BufferUnderflowException();
		}
		return backingArray[offset + position++];
	}

	public final int get (int index) {
		if (index < 0 || index >= limit) {
			throw new IndexOutOfBoundsException();
		}
		return backingArray[offset + index];
	}

	public final IntBuffer get (int[] dest, int off, int len) {
		int length = dest.length;
		if (off < 0 || len < 0 || (long)len + (long)off > length) {
			throw new IndexOutOfBoundsException();
		}
		if (len > remaining()) {
			throw new BufferUnderflowException();
		}
		System.arraycopy(backingArray, offset + position, dest, off, len);
		position += len;
		return this;
	}

	public final boolean isDirect () {
		return false;
	}

	public final ByteOrder order () {
		return ByteOrder.nativeOrder();
	}

}
