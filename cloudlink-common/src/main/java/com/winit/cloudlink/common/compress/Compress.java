package com.winit.cloudlink.common.compress;

import com.winit.cloudlink.common.exception.CompressionException;
import com.winit.cloudlink.common.utils.StringUtils;

public interface Compress {

    /**
     * 压缩
     *
     * @param data 待压缩数据
     * @return byte[] 压缩后的数据
     */
    public byte[] compress(byte[] data) throws CompressionException;

    /**
     * 解压缩
     *
     * @param data 待压缩的数据
     * @return byte[] 解压缩后的数据
     */
    public byte[] decompress(byte[] data) throws CompressionException;

    /**
     * 压缩类型
     *
     * @version <pre>
     * Author	Version		Date		Changes
     * jianke.zhang 	1.0  		2015年12月9日 	Created
     *
     * </pre>
     * @since 1.
     */
    public enum CompressCodec {
        ZLIB, GZIP, NONE;

        public static CompressCodec nameOf(String name) {
            if (StringUtils.isBlank(name)) {
                return CompressCodec.NONE;
            }
            name = name.trim();
            String compressCodec = String.valueOf(name.toUpperCase());
            if (CompressCodec.ZLIB.name().equals(compressCodec)) {
                return CompressCodec.ZLIB;
            } else if (CompressCodec.GZIP.name().equals(compressCodec)) {
                return CompressCodec.GZIP;
            } else {
                return CompressCodec.NONE;
            }
        }
    }
}
