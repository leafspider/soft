package leafspider.template;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;

public class TemplateProperties extends Properties
{      
	protected Properties defaults;   
    private static final int NONE = 0, SLASH = 1, UNICODE = 2, CONTINUE = 3, KEY_DONE = 4, IGNORE = 5;

    public TemplateProperties() {
    	super();
	}

    public synchronized void load(InputStream in) throws IOException 
    {
       if (in == null) {
           throw new NullPointerException();
       }
       BufferedInputStream bis = new BufferedInputStream(in);
       bis.mark(Integer.MAX_VALUE);
       boolean isEbcdic = isEbcdic(bis);
       bis.reset();
   
       if(!isEbcdic){
               load(new InputStreamReader(bis, "ISO8859-1")); //$NON-NLS-1$
       }else{
           load(new InputStreamReader(bis)); //$NON-NLS-1$
       }
   }
    
    private boolean isEbcdic(BufferedInputStream in) throws IOException
    {
     byte b;
     while ((b = (byte) in.read()) != -1) {
         if (b == 0x23 || b == 0x0a || b == 0x3d) {//ascii: newline/#/=
             return false;
         }
         if (b == 0x15) {//EBCDIC newline
             return true;
         }
     }
     //we found no ascii newline, '#', neither '=', relative safe to consider it
     //as non-ascii, the only exception will be a single line with only key(no value and '=')
     //in this case, it should be no harm to read it in default charset
     return false;
    }

	public synchronized void load(Reader reader) throws IOException 
	{
            int mode = NONE, unicode = 0, count = 0;
            char nextChar, buf[] = new char[40];
            int offset = 0, keyLength = -1, intVal;
            boolean firstChar = true;
            BufferedReader br = new BufferedReader(reader);
    
            while (true) 
            {
                intVal = br.read();
                if (intVal == -1) break;
                nextChar = (char) intVal;
    
                if (offset == buf.length) {
                    char[] newBuf = new char[buf.length * 2];
                    System.arraycopy(buf, 0, newBuf, 0, offset);
                    buf = newBuf;
                }
                if (mode == UNICODE) {
                    int digit = Character.digit(nextChar, 16);
                    if (digit >= 0) {
                        unicode = (unicode << 4) + digit;
                        if (++count < 4) {
                            continue;
                        }
                    } else if (count <= 4) {
                        // luni.09=Invalid Unicode sequence: illegal character
//                        throw new IllegalArgumentException(Messages.getString("luni.09"));
                        throw new IllegalArgumentException( "Invalid Unicode sequence: illegal character" );	
                    }
                    mode = NONE;
                    buf[offset++] = (char) unicode;
                    if (nextChar != '\n' && nextChar != '\u0085') {
                        continue;
                    }
                }
                if (mode == SLASH) {
                    mode = NONE;
                    switch (nextChar) {
                    case '\r':
                        mode = CONTINUE; // Look for a following \n
                        continue;
                    case '\u0085':
                case '\n':
                    mode = IGNORE; // Ignore whitespace on the next line
                    continue;
                case 'b':
                    nextChar = '\b';
                    break;
                case 'f':
                    nextChar = '\f';
                    break;
                case 'n':
                    nextChar = '\n';
                    break;
                case 'r':
                    nextChar = '\r';
                    break;
                case 't':
                    nextChar = '\t';
                    break;
                case 'u':
                    mode = UNICODE;
                    unicode = count = 0;
                    continue;
                }
            } else {
                switch (nextChar) {
                case '#':
                case '!':
                    if (firstChar) {
                        while (true) {
                            intVal = br.read();
                            if (intVal == -1) break;
                            nextChar = (char) intVal; // & 0xff
                                                                    // not
                                                                    // required
                            if (nextChar == '\r' || nextChar == '\n' || nextChar == '\u0085') {
                                break;
                            }
                        }
                        continue;
                    }
                    break;
                case '\n':
                    if (mode == CONTINUE) { // Part of a \r\n sequence
                        mode = IGNORE; // Ignore whitespace on the next line
                        continue;
                    }
                // fall into the next case
                case '\u0085':
                case '\r':
                    mode = NONE;
                    firstChar = true;
                    if (offset > 0 || (offset == 0 && keyLength == 0)) {
                        if (keyLength == -1) {
                            keyLength = offset;
                        }
                        String temp = new String(buf, 0, offset);
                        put(temp.substring(0, keyLength), temp
                                .substring(keyLength));
                    }
                    keyLength = -1;
                    offset = 0;
                    continue;
                case '\\':
                    if (mode == KEY_DONE) {
                        keyLength = offset;
                    }
                    mode = SLASH;
                    continue;
                case ':':
                case '=':
                    if (keyLength == -1) { // if parsing the key
                        mode = NONE;
                        keyLength = offset;
                        continue;
                    }
                    break;
                }
            	/* jmh 2010-09-02
                if (Character.isWhitespace(nextChar)) {
                    if (mode == CONTINUE) {
                        mode = IGNORE;
                    }
                    // if key length == 0 or value length == 0
                    if (offset == 0 || offset == keyLength || mode == IGNORE) {
                        continue;
                    }
                    if (keyLength == -1) { // if parsing the key
                        mode = KEY_DONE;
                        continue;
                    }
                }
                    */
                if (mode == IGNORE || mode == CONTINUE) {
                    mode = NONE;
                }
            }
            firstChar = false;
            if (mode == KEY_DONE) {
                keyLength = offset;
                mode = NONE;
            }
            buf[offset++] = nextChar;
        }
        if (mode == UNICODE && count <= 4) {
            // luni.08=Invalid Unicode sequence: expected format \\uxxxx
//            throw new IllegalArgumentException(Messages.getString("luni.08"));
            throw new IllegalArgumentException( "Invalid Unicode sequence: expected format \\uxxxx" );	
        }
        if (keyLength == -1 && offset > 0) {
            keyLength = offset;
        }
        if (keyLength >= 0) {
            String temp = new String(buf, 0, offset);
            String key = temp.substring(0, keyLength);
            String value = temp.substring(keyLength);
            if (mode == SLASH) {
                value += "\u0000";
            }
            put(key, value);
//            System.out.println( "key=" + key );
        }
    }   
	
}
