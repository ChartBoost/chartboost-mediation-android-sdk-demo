package com.chartboost.helium.helium_common.version;


import com.chartboost.helium.helium_common.value.string.StringValue;

public class QVersion implements Version<StringValue>{
    private final StringValue major_;
    private final StringValue minor_;
    private final StringValue patch_;
    private final StringValue build_;

    private QVersion(StringValue major, StringValue minor,
                     StringValue patch, StringValue build) {
        major_ = StringValue.valueOf(major.asString());
        minor_ = StringValue.valueOf(minor.asString());
        patch_ = StringValue.valueOf(patch.asString());
        build_ = StringValue.valueOf(build.asString());
    }
    private QVersion() {
        this(StringValue.valueOf(""),
            StringValue.valueOf(""),
            StringValue.valueOf(""),
            StringValue.valueOf(""));
    }

    public static QVersion from(StringValue major, StringValue minor,
                         StringValue patch, StringValue build) {
        return new QVersion(major, minor, patch, build);
    }

    public static QVersion copyOf(QVersion version) {
        return new QVersion(version.major(), version.minor(), version.patch(), version.build());
    }

    public static QVersion empty() {
        return new QVersion();
    }



    @Override
    public StringValue versionAsString() {
        return null;
    }

    public StringValue major() {
        return major_;
    }

    public StringValue minor() {
        return minor_;
    }

    public StringValue patch() {
        return patch_;
    }

    public StringValue build() {
        return build_;
    }
}
