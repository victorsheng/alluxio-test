package alluxio.client.file;

import alluxio.AlluxioURI;
import alluxio.Configuration;
import alluxio.Constants;
import alluxio.PropertyKey;
import alluxio.client.WriteType;
import alluxio.client.file.options.CreateDirectoryOptions;
import alluxio.client.file.options.CreateFileOptions;
import alluxio.exception.AlluxioException;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;

public class ATest {

  private CreateFileOptions fileOptions;
  private CreateDirectoryOptions directoryOptions;

  @Before
  public void init() {
    this.host = "localhost";
    this.port = "19998";
    this.user = "victor";
    this.initAlluxio();
  }



  private String host;
  private String port;
  private String user;

  private void initAlluxio() {
    Configuration.set(PropertyKey.MASTER_HOSTNAME, host);
    Configuration.set(PropertyKey.MASTER_RPC_PORT, port);
    Configuration.set(PropertyKey.SECURITY_LOGIN_USERNAME, user);

//    WriteType mustCache = WriteType.MUST_CACHE;
//    WriteType mustCache = WriteType.THROUGH;
    WriteType mustCache = WriteType.THROUGH;
    fileOptions = CreateFileOptions.defaults().setWriteType(mustCache)
        .setBlockSizeBytes(64 * Constants.MB);
    directoryOptions = CreateDirectoryOptions.defaults().setWriteType(mustCache);
  }


  @Test
  public void write() throws IOException, AlluxioException {
    FileSystem fs = FileSystem.Factory.get();
    AlluxioURI path = new AlluxioURI("/mytmp1");
    FileOutStream file = fs.createFile(path,fileOptions);
    byte[] bytes = "hello world".getBytes();
    file.write(bytes);
    file.close();
  }


  @Test
//  @After
  public void delete() throws IOException, AlluxioException {
    FileSystem fs = FileSystem.Factory.get();
    AlluxioURI path = new AlluxioURI("/mytmp1");
    fs.delete(path);
  }

  @Test
  public void read() throws IOException, AlluxioException {
    FileSystem fs = FileSystem.Factory.get();
    AlluxioURI path = new AlluxioURI("/mytmp1");
    FileInStream fileInStream = fs.openFile(path);
    byte[] bytes = new byte[100];
    fileInStream.read(bytes);
    fileInStream.close();
    String str = new String(bytes);
    System.out.println(str);
  }

  @Test
  public void mkdir() throws IOException, AlluxioException {
    FileSystem fs = FileSystem.Factory.get();
    AlluxioURI path = new AlluxioURI("/mydir");
    fs.createDirectory(path);
  }

  @Test
  public void rmdir() throws IOException, AlluxioException {
    FileSystem fs = FileSystem.Factory.get();
    AlluxioURI path = new AlluxioURI("/mydir");
    fs.delete(path);
  }

  @Test
  public void dirExists() throws IOException, AlluxioException {
    FileSystem fs = FileSystem.Factory.get();
    AlluxioURI path = new AlluxioURI("/mydir");
    fs.exists(path);
  }

  @Test
  public void rename1() throws IOException, AlluxioException {
    FileSystem fs = FileSystem.Factory.get();
    AlluxioURI path = new AlluxioURI("/mydir");
    AlluxioURI path2 = new AlluxioURI("/mydir2");
    fs.rename(path,path2);
  }

  @Test
  public void rename2() throws IOException, AlluxioException {
    FileSystem fs = FileSystem.Factory.get();
    AlluxioURI path = new AlluxioURI("/mydir");
    AlluxioURI path2 = new AlluxioURI("/mydir2");
    fs.rename(path2,path);
  }
}
