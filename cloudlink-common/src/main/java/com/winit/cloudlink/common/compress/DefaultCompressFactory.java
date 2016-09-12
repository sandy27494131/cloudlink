package com.winit.cloudlink.common.compress;

import java.util.HashMap;
import java.util.Map;

import com.winit.cloudlink.common.compress.Compress.CompressCodec;
import com.winit.cloudlink.common.exception.CompressionException;

public class DefaultCompressFactory implements CompressFactory {

    private static Map<CompressCodec, Compress> compressMap = new HashMap<CompressCodec, Compress>();

    static {
        compressMap.put(CompressCodec.GZIB, new GZipCompress());
        compressMap.put(CompressCodec.ZLIB, new ZLibCompress());
    }

    public DefaultCompressFactory(){
    }

    @Override
    public byte[] compress(CompressCodec type, byte[] data) throws CompressionException {
        if (null == type || null == data || CompressCodec.NONE.equals(type)) {
            return data;
        }

        Compress compress = compressMap.get(type);
        if (null != compress) {
            return compress.compress(data);
        } else {
            return data;
        }

    }

    @Override
    public byte[] decompress(CompressCodec type, byte[] data) throws CompressionException {
        if (null == type || null == data || CompressCodec.NONE.equals(type)) {
            return data;
        }

        Compress compress = compressMap.get(type);
        if (null != compress) {
            return compress.decompress(data);
        } else {
            return data;
        }
    }

}
