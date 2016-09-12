package com.winit.cloudlink.common.compress;

import com.winit.cloudlink.common.compress.Compress.CompressCodec;
import com.winit.cloudlink.common.exception.CompressionException;

public interface CompressFactory {

    public byte[] compress(CompressCodec type, byte[] data) throws CompressionException;

    public byte[] decompress(CompressCodec type, byte[] data) throws CompressionException;
}
