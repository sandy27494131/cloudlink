package com.winit.cloudlink.common.compress;

import com.winit.cloudlink.common.Constants;
import com.winit.cloudlink.common.exception.CompressionException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class ZLibCompress implements Compress {
    private int level = Constants.DEFAULT_COMPRESSION;

    /**
     * 压缩
     *
     * @param data 待压缩数据
     * @return byte[] 压缩后的数据
     */
    @Override

    public byte[] compress(byte[] data) throws CompressionException {
        if (null == data) return null;
        byte[] output = new byte[0];

        Deflater compresser = new Deflater(level, true);

        compresser.reset();
        compresser.setInput(data);
        compresser.finish();
        ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length);
        try {
            byte[] buf = new byte[1024];
            while (!compresser.finished()) {
                int i = compresser.deflate(buf);
                bos.write(buf, 0, i);
            }
            output = bos.toByteArray();
        } catch (Exception e) {
            output = data;
            throw new CompressionException(e);
        } finally {
            try {
                bos.close();
            } catch (IOException e) {
                // ignore
            }
        }
        compresser.end();
        return output;
    }

    /**
     * 解压缩
     *
     * @param data 待压缩的数据
     * @return byte[] 解压缩后的数据
     */
    @Override
    public byte[] decompress(byte[] data) throws CompressionException {
        if (null == data) return null;
        byte[] output = new byte[0];

        Inflater decompresser = new Inflater(true);
        decompresser.reset();
        decompresser.setInput(data);

        ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length);
        try {
            byte[] buf = new byte[1024];
            while (!decompresser.finished()) {
                int i = decompresser.inflate(buf);
                bos.write(buf, 0, i);
            }
            output = bos.toByteArray();
        } catch (Exception e) {
            output = data;
            throw new CompressionException(e);
        } finally {
            try {
                bos.close();
            } catch (IOException e) {
                // ignore
            }
        }

        decompresser.end();
        return output;
    }

}
