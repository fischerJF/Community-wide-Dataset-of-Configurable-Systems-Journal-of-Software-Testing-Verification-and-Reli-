import java.io.IOException; 
import java.io.InputStream; 






/** 
 * InputStream that computes a checksum of the data being read using a
 * supplied Checksum object.
 * @see Checksum
 * @author Tom Tromey
 * @date May 17, 1999
 */
public  class  CheckedInputStream  extends InputStream {
	
	
  /** 
 * This is the subordinate <code>InputStream</code> to which method calls
 * are redirected
 */
  protected InputStream in;

	
  
  /** 
 * Creates a new CheckInputStream on top of the supplied OutputStream
 * using the supplied Checksum.
 */
  public CheckedInputStream(  InputStream in,  Checksum sum){
	    this.in=in;
	    this.sum=sum;
  }

	
  
  /** 
 * Returns the Checksum object used. To get the data checksum computed so
 * far call <code>getChecksum.getValue()</code>.
 */
  public Checksum getChecksum(){
		  return sum;
  }

	
  
  /** 
 * Reads one byte, updates the checksum and returns the read byte
 * (or -1 when the end of file was reached).
 */
  public int read() throws IOException {
	    int x=in.read();
	    if (x != -1)     sum.update(x);
	    return x;
  }

	
  
  /** 
 * Calls the <code>read(byte[], int, int)</code> overloaded method.  
 * Note that 
 * this method does not redirect its call directly to a corresponding
 * method in <code>in</code>.  This allows subclasses to override only the
 * three argument version of <code>read</code>.
 * @param buf The buffer to read bytes into
 * @return The value retured from <code>in.read(byte[], int, int)</code>
 * @exception IOException If an error occurs
 */
  public int read(  byte[] buf) throws IOException {
		  return read(buf,0,buf.length);
  }

	
  
  /** 
 * Reads at most len bytes in the supplied buffer and updates the checksum
 * with it. Returns the number of bytes actually read or -1 when the end
 * of file was reached.
 */
  public int read(  byte[] buf,  int off,  int len) throws IOException {
	    int r=in.read(buf,off,len);
	    if (r != -1)     sum.update(buf,off,r);
	    return r;
  }

	
  
  /** 
 * Skips n bytes by reading them in a temporary buffer and updating the
 * the checksum with that buffer. Returns the actual number of bytes skiped
 * which can be less then requested when the end of file is reached.
 */
  public long skip(  long n) throws IOException {
	    if (n == 0)     return 0;
	    int min=(int)Math.min(n,1024);
	    byte[] buf=new byte[min];
	    long s=0;
	    while (n > 0) {
	      int r=in.read(buf,0,min);
	      if (r == -1)       break;
	      n-=r;
	      s+=r;
	      min=(int)Math.min(n,1024);
	      sum.update(buf,0,r);
	    }
	    return s;
  }

	
  
  /** 
 * Calls the <code>in.mark(int)</code> method.
 * @param readlimit The parameter passed to <code>in.mark(int)</code>
 */
  public void mark(  int readlimit){
		  in.mark(readlimit);
  }

	
  
  /** 
 * Calls the <code>in.markSupported()</code> method.
 * @return <code>true</code> if mark/reset is supported, <code>false</code>
 * otherwise
 */
  public boolean markSupported(){
		  return in.markSupported();
  }

	
  
  /** 
 * Calls the <code>in.reset()</code> method.
 * @exception IOException If an error occurs
 */
  public void reset() throws IOException {
		  in.reset();
  }

	
  
  /** 
 * Calls the <code>in.available()</code> method.
 * @return The value returned from <code>in.available()</code>
 * @exception IOException If an error occurs
 */
  public int available() throws IOException {
		  return in.available();
  }

	
  
  /** 
 * This method closes the input stream by closing the input stream that
 * this object is filtering.  Future attempts to access this stream may
 * throw an exception.
 * @exception IOException If an error occurs
 */
  public void close() throws IOException {
		  in.close();
  }

	
  /** 
 * The checksum object. 
 */
  private Checksum sum;


}
