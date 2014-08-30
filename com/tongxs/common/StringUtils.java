package com.tongxs.common;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;

/**
 * GUID���ɣ�������ȹ��߷���
 * 
 * @since 2011-6-11
 * @author slim Copyright (c) 2011, �������������޹�˾����ý�忪����PTC����ƽ̨��

 */
public class StringUtils {

	/**
	 * toString�ı�ݰ汾

	 * 
	 * @param obj
	 * @return
	 */
	public static String toString( Object obj ) {
		if ( obj == null )
			return null;
		StringBuilder sb = new StringBuilder( 100 );
		Class<? extends Object> clazz = obj.getClass();
		sb.append( "[" );
		for ( Field field : clazz.getDeclaredFields() ) {
			try {
				if(Modifier.isStatic( field.getModifiers() ))
					continue;
				else {
					field.setAccessible( true );
					sb.append( field.getName() ).append( "=" ).append( field.get( obj ) ).append( ";" );
				}
			}
			catch ( Exception e ) {
				sb.append( "Field:" ).append( field.getName() ).append( " Get Value Err" ).append( e );
				sb.append( ";" );
			}
		}
		sb.append( "]" );
		return sb.toString();
	}

	/**
	 * ��ȡΨһ��Guid
	 * 
	 * @return
	 */
	public synchronized static String getGuid() {
		return java.util.UUID.randomUUID().toString();
	}

	/**
	 * ������еı�������
	 */
	public static void printCharsetNames() {
		for ( String charsetName : Charset.availableCharsets().keySet() ) {
			System.out.println( charsetName );
		}
	}

	/**
	 * �жϸ����ı��������Ƿ���Ч

	 * 
	 * @param charsetName
	 * @return
	 */
	public static boolean isValidCharset( String charsetName ) {
		Object o = Charset.availableCharsets().get( charsetName );
		if ( null != o )
			return true;
		return false;
	}

	/**
	 * ���˵�ָ���ַ��������ָ��������Ч���ַ�
	 * 
	 * @param input ��Ҫ���˵��ַ���

	 * @param charsetName �����ı�������

	 * @return
	 * @throws CharacterCodingException
	 */
	public static String filterUnmappableChar( String input, String charsetName )
			throws CharacterCodingException {
		if ( !isValidCharset( charsetName ) ) {
			throw new java.nio.charset.UnsupportedCharsetException( charsetName
					+ " is not a valid charsetName" );
		}
		Charset charset = Charset.forName( charsetName );
		CharsetEncoder encoder = charset.newEncoder();
		encoder.onUnmappableCharacter( CodingErrorAction.IGNORE );
		CharsetDecoder decoder = charset.newDecoder();
		CharBuffer buffer = CharBuffer.allocate( 32 );
		buffer.put( input );
		buffer.flip();
		ByteBuffer byteBuffer = encoder.encode( buffer );
		CharBuffer cbuf = decoder.decode( byteBuffer );
		return cbuf.toString();
	}
	
	public static long[] toLongArray(String[] StringArray){
		long[] longArray = null;
		try{
			if(StringArray != null){
	        longArray = new long[StringArray.length];
	        for (int idx = 0; idx < StringArray.length; idx++) {
	        	Long tmp = Long.parseLong(StringArray[idx]);
	        	longArray[idx] = tmp.longValue();
	        }

			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return longArray;
	}
}
