package com.chartboost.helium.helium_common.common;

public class SimpleBuffer {
    private byte[] rawBytes;
    private int bytesLength;
    private String tag;

    public SimpleBuffer(byte[] rawBytes, int bytesLength, String tag) {
        this.rawBytes = rawBytes;
        this.bytesLength = bytesLength;
        this.tag = tag;
    }

    public byte[] getRawBytes() {
        return rawBytes;
    }

    public void setRawBytes(byte[] rawBytes) {
        this.rawBytes = rawBytes;
    }

    public int getBytesLength() {
        return bytesLength;
    }

    public void setBytesLength(int bytesLength) {
        this.bytesLength = bytesLength;
    }

    public String asString() {
        return this.tag;
    }
}
