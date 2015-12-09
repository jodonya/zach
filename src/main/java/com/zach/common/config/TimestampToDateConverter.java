package com.zach.common.config;

import java.sql.Date;
import java.sql.Timestamp;

import org.springframework.core.convert.converter.Converter;

public enum TimestampToDateConverter implements Converter<Timestamp, Date> {
	INSTANCE;

	@Override
	public Date convert(Timestamp source) {
		 return source == null ? null : new Date(source.getTime());
	}

}
