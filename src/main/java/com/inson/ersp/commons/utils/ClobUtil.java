package com.inson.ersp.commons.utils;
import javax.sql.rowset.serial.SerialClob;
import javax.sql.rowset.serial.SerialException;
import java.sql.Clob;
import java.sql.SQLException;
import java.io.Reader;
import java.io.IOException;

public class ClobUtil {

    public static String convertClobToString(Clob clob) throws SQLException, IOException {
        StringBuilder sb = new StringBuilder();
        Reader reader = clob.getCharacterStream();
        char[] buffer = new char[2048];
        int bytesRead;
        while ((bytesRead = reader.read(buffer, 0, buffer.length)) != -1) {
            sb.append(buffer, 0, bytesRead);
        }
        return sb.toString();
    }

    public static Clob convertStringToClob(String data) throws SerialException, SQLException {
        if (data == null) {
            return null;
        }
        return new SerialClob(data.toCharArray());
    }
}
