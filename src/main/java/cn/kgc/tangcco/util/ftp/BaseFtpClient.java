package cn.kgc.tangcco.util.ftp;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
/**
 * @author	作者 : 李昊哲
 * @version	创建时间：2019年3月20日 下午9:23:29
  *  类说明
 */
public class BaseFtpClient {
	private static Logger logger = LoggerFactory.getLogger(BaseFtpClient.class);
    private FTPClient ftpClient;

    /**
     * 连接远程FTP服务器
     * @param Servcer   服务器都中
     * @param username  服务器登录账号
     * @param password  服务器登录密码
     * @return
     */
    public boolean connectServer(String Servcer, String username, String password){
        boolean  res = false;
        try {
            ftpClient = new FTPClient();
            ftpClient.connect(Servcer);
            boolean login = ftpClient.login(username, password);
            if(!login){
                ftpClient.enterLocalPassiveMode();
                logger.error("FtpClientUtils -> connectServer    ftp login failed! ftpConfig[{},{},{}]", new Object[]{ Servcer, username, password});
                return res;
            }
            logger.info("FtpClientUtils conneted {} server ." ,  username);
            
            int replyCode = ftpClient.getReplyCode();
            if(!FTPReply.isPositiveCompletion(replyCode)){
                ftpClient.disconnect();
                logger.error("FTP server refused connection.");
                return res;
            }
            
            //设置缓冲大小
            ftpClient.setBufferSize(2048);
            ftpClient.setControlEncoding("UTF-8");
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            res =  true;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            if(ftpClient != null && ftpClient.isConnected()){
                try {
                    ftpClient.disconnect();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
            logger.error("###FtpClientUtils -> connectServer failed!!!!");
        }
        return res;
        
    }
    
    /**
     * 断开
     */
    public void disconnectServer(){
        if(ftpClient != null && ftpClient.isConnected()){
            try {
                ftpClient.disconnect();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
        
        logger.debug("disconnectServer success!");
    }
    
    
    public boolean uploadFile(String fileName, InputStream is){
        boolean  res = false;
        if(ftpClient != null && ftpClient.isConnected()){
            try {
                //如果名称含有文件夹刚先创建文件夹
                if(fileName.indexOf("/") > -1){

                    String parentPath = fileName.substring(0, fileName.lastIndexOf("/"));
                    String[] filePaths = parentPath.split("/");
                    String dirPath = "";
                    //出现多层目录时需要一层层建目录
                    for(String dirName : filePaths){
                        if(!dirName.equals("") ){
                            dirPath = dirPath + "/" + dirName;
                            ftpClient.makeDirectory(dirPath);
                        }
                    }
                    logger.debug("FtpClientUtils->uploadFile  [{}] makedir success!->" );
                
                }
                res = ftpClient.storeFile(fileName, is);
                is.close();
                if(res){
                    logger.info("uploadFile[{}] success! ", fileName);
                }else{
                    logger.error("uploafile[{}] failed! ", fileName);
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                logger.error("FtpClientUtils->uploadFile failed!!!");
            }
            
        }
        return res;
    }
}
