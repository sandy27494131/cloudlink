package com.winit.cloudlink.common.compress;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import com.winit.cloudlink.common.exception.CompressionException;

public class GZipCompress implements Compress {

    public final int BUFFER = 1024;

    /**
     * 数据压缩
     * 
     * @param data
     * @return
     * @throws Exception
     */
    @Override
    public byte[] compress(byte[] data) throws CompressionException {
        if (null == data) return null;
        ByteArrayInputStream bais = null;
        ByteArrayOutputStream baos = null;
        byte[] output = null;
        try {
            bais = new ByteArrayInputStream(data);
            baos = new ByteArrayOutputStream();
            // 压缩
            compress(bais, baos);

            output = baos.toByteArray();

            baos.flush();
        } catch (Exception e) {
            output = data;
            throw new CompressionException(e);
        } finally {
            if (null != baos) {
                try {
                    baos.close();
                } catch (Exception e2) {
                }
            }
            if (null != baos) {
                try {
                    bais.close();
                } catch (Exception e2) {
                }
            }

        }

        return output;
    }

    /**
     * 数据压缩
     * 
     * @param is
     * @param os
     * @throws Exception
     */
    private void compress(InputStream is, OutputStream os) throws Exception {
        GZIPOutputStream gos = null;
        try {
            gos = new GZIPOutputStream(os);
            int count;
            byte data[] = new byte[BUFFER];
            while ((count = is.read(data, 0, BUFFER)) != -1) {
                gos.write(data, 0, count);
            }
            gos.finish();
            gos.flush();
        } finally {
            if (null != gos) {
                try {
                    gos.close();
                } catch (Exception e) {
                }
            }

        }

    }

    /**
     * 数据解压缩
     * 
     * @param data
     * @return
     * @throws Exception
     */
    @Override
    public byte[] decompress(byte[] data) throws CompressionException {
        if (null == data) return null;
        ByteArrayInputStream bais = null;
        ByteArrayOutputStream baos = null;
        byte[] output = null;
        try {
            bais = new ByteArrayInputStream(data);
            baos = new ByteArrayOutputStream();
            decompress(bais, baos);

            output = baos.toByteArray();

            baos.flush();
        } catch (Exception e) {
            output = data;
            throw new CompressionException(e);
        } finally {
            if (null != baos) {
                try {
                    baos.close();
                } catch (Exception e2) {
                }
            }
            if (null != bais) {
                try {
                    bais.close();
                } catch (Exception e2) {
                }
            }
        }
        return output;
    }

    /**
     * 数据解压缩
     * 
     * @param is
     * @param os
     * @throws Exception
     */
    private void decompress(InputStream is, OutputStream os) throws Exception {

        GZIPInputStream gis = null;
        try {
            gis = new GZIPInputStream(is);
            int count;
            byte data[] = new byte[BUFFER];
            while ((count = gis.read(data, 0, BUFFER)) != -1) {
                os.write(data, 0, count);
            }
        } finally {
            if (null != gis) {
                try {
                    gis.close();
                } catch (Exception e) {
                }
            }
        }
    }

}
