package org.akj.mongo.bean;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateCodec implements Codec<Date> {
    @Override
    public Date decode(BsonReader reader, DecoderContext decoderContext) {
        Date date = null;
        try {
            reader.readStartDocument();
            date = new SimpleDateFormat("yyyy-mm-dd hh:MM:ss").parse(reader.readString("dateTime"));
            reader.readEndDocument();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    @Override
    public void encode(BsonWriter writer, Date value, EncoderContext encoderContext) {
        writer.writeStartDocument();
        writer.writeString("dateTime", new SimpleDateFormat("yyyy-mm-dd hh:MM:ss").format(value));
        writer.writeEndDocument();
    }

    @Override
    public Class<Date> getEncoderClass() {
        return Date.class;
    }
}
