package com.zz.nio;


import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;


/**
 * @author : Zak
 * @date : 2016年5月31日 下午3:23:54
 * @version : 2016年5月31日 Zak 首次创建
 */
public class FileChannelTest {
  public static void main( String[] args ) throws Exception {
    System.out.println(System.getProperty("user.dir") + "\n");
    RandomAccessFile aFile = new RandomAccessFile("src/main/resources/data/nio-data.txt", "rw");
    FileChannel channel = aFile.getChannel();
    ByteBuffer buf = ByteBuffer.allocate(48);

    int bytesRead = channel.read(buf);
    while(bytesRead != -1) {
      buf.flip();
      while(buf.hasRemaining()) {// 不断循环读
        System.out.print((char) buf.get());
      }

      buf.clear();
      bytesRead = channel.read(buf);
    }
    String str = "\n" + String.valueOf(Math.random());
    buf = ByteBuffer.wrap(str.getBytes());
    // 比较坑，wrap后postion=0,limit=capacity=array.length,所以不再需要flip
    // buf.flip();
    while(buf.hasRemaining()) {// 不断循环写
      channel.write(buf);
    }
    channel.close();
    aFile.close();

  }

}
