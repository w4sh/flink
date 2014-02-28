package eu.stratosphere.hadoopcompatibility.datatypes;

import eu.stratosphere.types.*;
import org.apache.hadoop.io.*;

import java.lang.reflect.ParameterizedType;

/**
 * Converter Stratosphere Record into the default hadoop writables.
 *
 */
public class DefaultStratosphereTypeConverter<K,V> implements StratosphereTypeConverter<K,V> {
	private static final long serialVersionUID = 1L;

	private Class<K> keyClass;
	private Class<V> valueClass;

	public DefaultStratosphereTypeConverter(Class<K> keyClass, Class<V> valueClass) {
		this.keyClass= keyClass;
		this.valueClass = valueClass;
	}
	@Override
	public K convertKey(Record stratosphereRecord) {
		return convert(stratosphereRecord, 0, this.keyClass);
	}

	@Override
	public V convertValue(Record stratosphereRecord) {
		return convert(stratosphereRecord, 1, this.valueClass);
	}

	private<T> T convert(Record stratosphereType, int pos, Class<T> hadoopType) {
		if(hadoopType == LongWritable.class ) {
			return (T) new LongWritable((stratosphereType.getField(pos, LongValue.class)).getValue());
		}
		if(hadoopType == org.apache.hadoop.io.Text.class) {
			return (T) new Text((stratosphereType.getField(pos, StringValue.class)).getValue());
		}
		if(hadoopType == org.apache.hadoop.io.IntWritable.class) {
			return (T) new IntWritable((stratosphereType.getField(pos, IntValue.class)).getValue());
		}
		if(hadoopType == org.apache.hadoop.io.FloatWritable.class) {
			return (T) new FloatWritable((stratosphereType.getField(pos, FloatValue.class)).getValue());
		}
		if(hadoopType == org.apache.hadoop.io.DoubleWritable.class) {
			return (T) new DoubleWritable((stratosphereType.getField(pos, DoubleValue.class)).getValue());
		}
		if(hadoopType == org.apache.hadoop.io.BooleanWritable.class) {
			return (T) new BooleanWritable((stratosphereType.getField(pos, BooleanValue.class)).getValue());
		}
		if(hadoopType == org.apache.hadoop.io.ByteWritable.class) {
			return (T) new ByteWritable((stratosphereType.getField(pos, ByteValue.class)).getValue());
		}

		throw new RuntimeException("Unable to convert Stratosphere type ("+stratosphereType.getClass().getCanonicalName()+") to Hadoop.");
	}
}
