package com.user.utils;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@MappedTypes({String.class})
@MappedJdbcTypes(JdbcType.VARCHAR)
public class StringTypeHandler extends org.apache.ibatis.type.StringTypeHandler {

	private static final Logger log = LoggerFactory.getLogger(StringTypeHandler.class);

	@Override
	public void setParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
		// TODO Auto-generated method stub
		System.out.println("use myself StringTypeHandler");
		System.out.println(" parameter =" +parameter);
		super.setParameter(ps, i, parameter, jdbcType);
		
	}

	@Override
	public String getResult(ResultSet rs, String columnName) throws SQLException {
		// TODO Auto-generated method stub
		System.out.println(" parameter =" +columnName);
		return super.getResult(rs, columnName);
	}

	@Override
	public String getResult(ResultSet rs, int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		System.out.println(" parameter =" +columnIndex);
		return super.getResult(rs, columnIndex);
	}

	@Override
	public String getResult(CallableStatement cs, int columnIndex) throws SQLException {
		 
		return super.getResult(cs, columnIndex);
	}
		 
}
