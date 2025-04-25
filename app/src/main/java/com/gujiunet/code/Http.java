package com.gujiunet.code;

import com.gujiunet.code.util.Files;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;
public class Http {


    private String url = "";
    private String data = "";
    private String cookie = "";
    private String charset = "UTF-8";
    private String filePath = "";
    private long contentLength = -1;

    private HashMap<String,String> header = new HashMap<>();

    public static int REQUEST_TYPE_GET = 0x1;
    public static int REQUEST_TYPE_POST = 0x2;
    public static int REQUEST_TYPE_DOWNLOAD = 0x3;
    public static int REQUEST_TYPE_UPDATE = 0x4;
    public static HashMap<Integer, String> REQUEST_TYPE_LIST = new HashMap();

    static {
        REQUEST_TYPE_LIST.put(REQUEST_TYPE_GET, "GET");
        REQUEST_TYPE_LIST.put(REQUEST_TYPE_POST, "POST");
        REQUEST_TYPE_LIST.put(REQUEST_TYPE_DOWNLOAD, "DOWNLOAD");
        REQUEST_TYPE_LIST.put(REQUEST_TYPE_UPDATE, "UPDATE");
    }

    private int requestType;
    private RequestEvent requestEvent;
    private String updateFilePath;

    public void setPath(String updateFilePath) {
        this.updateFilePath = updateFilePath;
    }

    public interface RequestEvent {
        void requestComplete(int code,String content,String cookie);
        void downloadProgress(long length, double percentage);
        void updateProgress(long length,double percentage);

    }

    public Http setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getUrl() {
        return this.url;
    }

    public Http setData(String data) {
        this.data = data;
        return this;
    }

    public String getData() {
        return this.data;
    }

    public Http setCookie(String cookie) {
        this.cookie = cookie;
        return this;
    }

    public String getCookie() {
        return this.cookie;
    }

    public Http setCharset(String charset) {
        this.charset = charset;
        return this;
    }

    public String getCharset() {
        return this.charset;
    }

    public Http setFilePath(String filePath) {
        this.filePath = filePath;
        return this;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public Http setRequestType(int requestType) {
        this.requestType = requestType;
        return this;
    }

    public long getContentLength() {
        return contentLength;
    }

    public String getRequestType() {
        return REQUEST_TYPE_LIST.get(this.requestType);
    }

    public Http addHeader(String key, String value) {
        this.header.put(key,value);
        return this;
    }

    public Http clearHeader() {
        this.header.clear();
        return this;
    }

    public Http setRequest(RequestEvent requestEvent) {
        if(requestEvent != null) {
            this.requestEvent = requestEvent;
        }
        return this;
    }


    public class Request {
        int code = 0;
        String cookie;
        String content;

        public void setCode(int code) {
            this.code = code;
        }

        public int getCode() {
            return this.code;
        }

        public void setCookie(String cookie) {
            this.cookie = cookie;
        }

        public String getCookie() {
            return this.cookie;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getContent() {
            return content;
        }
    }


    public Request request() {

        Request request = new Request();
        contentLength = -1;

        try {
            byte[] bytesData;
            URL url = new URL(this.url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            if (this.url.startsWith("https://")) {
                conn = (HttpsURLConnection) url.openConnection();

                try {
                    HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                        public boolean verify(String hostname, SSLSession session) {
                            return true;
                        }
                    });

                    SSLContext context = SSLContext.getInstance("TLS");
                    context.init(null, new X509TrustManager[]{new X509TrustManager() {
                        public void checkClientTrusted(X509Certificate[] chain, String authType) {
                        }

                        public void checkServerTrusted(X509Certificate[] chain, String authType) {
                        }

                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[0];
                        }
                    }}, new SecureRandom());
                    HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(120000);
            HttpURLConnection.setFollowRedirects(true);
            conn.setDoInput(true);
            conn.setRequestProperty("Accept-Language", "zh-cn,zh;q=0.5");
            conn.setRequestProperty("Accept-Charset", this.charset);

            if (!this.cookie.isEmpty()) {
                conn.setRequestProperty("Cookie", this.cookie);
            }

            if (requestType == REQUEST_TYPE_GET) {
                //GET
                conn.setRequestMethod("GET");
            } else if (requestType == REQUEST_TYPE_POST) {
                //POST
                conn.setRequestMethod("POST");
            } else if (requestType == REQUEST_TYPE_DOWNLOAD) {
                //DOWNLOAD
                if(data!= null && !data.isEmpty()) {
                    conn.setRequestMethod("POST");
                }
            } else if (requestType == REQUEST_TYPE_UPDATE) {
                conn.setRequestProperty("Content-length", "" );
                //UPDATE
                conn.setDoInput(true); //允许输入流
                conn.setDoOutput(true); //允许输出流
                conn.setUseCaches(false); //不允许使用缓存
                conn.setRequestMethod("POST");
                String BOUNDARY = "******"; //边界标识 随机生成
                String PREFIX = "--", LINE_END = "\r\n";
                String CONTENT_TYPE = "multipart/form-data"; //内容类型
                conn.setRequestProperty("connection", "keep-alive");
                conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);
                OutputStream outputSteam = conn.getOutputStream();
                DataOutputStream dos = new DataOutputStream(outputSteam);
                StringBuffer sb = new StringBuffer();
                sb.append(PREFIX);
                sb.append(BOUNDARY);
                sb.append(LINE_END);
                if(data != null && !data.isEmpty()) {
                    String[] dataArray = data.split("&");
                    for (String s : dataArray) {
                        String key = s.substring(0,s.indexOf("="));
                        String value = s.substring(s.indexOf("=")+1);
                        sb.append("Content-Disposition: form-data; name=\" "+key +"\"" + LINE_END + LINE_END + value + LINE_END);
                    }
                }
                sb.append("Content-Disposition: form-data; name=\"file\"; " + LINE_END + "filename=\"" + new File(this.filePath).getName() + "\""
                        + LINE_END);
                sb.append("Content-Type: "+ Files.getFileType(new File(this.filePath).getName()) + LINE_END);
                sb.append(LINE_END);
                dos.write(sb.toString().getBytes());
                InputStream is = new FileInputStream(this.filePath);
                byte[] bytes = new byte[1024];
                int len;
                long updatedFileSize = 0;
                long localFileSize = is.available();
                while ((len = is.read(bytes)) != -1) {
                    if(requestType == REQUEST_TYPE_UPDATE && requestEvent != null) {
                        updatedFileSize += len;
                        //更新上传进度
                        requestEvent.updateProgress(updatedFileSize, (double) updatedFileSize /localFileSize*100);
                    }
                    dos.write(bytes, 0, len);
                }

                is.close();
                dos.write(LINE_END.getBytes());
                byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END).getBytes();
                dos.write(end_data);
                dos.flush();


            }


            if (data!= null && !data.isEmpty()) {
                if(requestType != REQUEST_TYPE_UPDATE) {

                    bytesData = formatData(new String[] { this.data });
                    conn.setDoOutput(true);
                    conn.setRequestProperty("Content-length", "" + bytesData.length);
                    conn.getOutputStream().write(bytesData);
                }
            }

            if (!this.header.isEmpty()) {
                Set<Map.Entry<String, String>> entries = this.header.entrySet();
                for (Map.Entry<String, String> entry : entries) {
                    conn.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }

            conn.connect();
            contentLength = conn.getContentLength();

            int code = conn.getResponseCode();
            Map<String, List<String>> hs = conn.getHeaderFields();
            if (code >= 200 && code < 400) {
                List<String> cs = hs.get("Set-Cookie");
                StringBuffer cok = new StringBuffer();
                if (cs != null)
                    for (String s : cs) {
                        cok.append(s).append(";");
                    }

                InputStream is = conn.getInputStream();
                if(requestType == REQUEST_TYPE_DOWNLOAD) {
                    //DOWNLOAD
                    //只指定下载路径
                    if(new File((filePath)).isDirectory()) {
                        String newDownloadFilePath = this.filePath + this.url.split("/")[this.url.split("/").length - 1];
                        new FileOutputStream(newDownloadFilePath).write(toByteArray(is));
                    } else {
                        new FileOutputStream(this.filePath).write(toByteArray(is));
                    }
                }
                BufferedReader reader = new BufferedReader(new InputStreamReader(is,this.charset));
                StringBuilder buf = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null)
                    buf.append(line);
                is.close();
                request.setCode(code);
                request.setContent(new String(buf));
                request.setCookie(cok.toString());
            } else {
                request.setCode(code);
                request.setContent(conn.getResponseMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setCode(-1);
            request.setContent(e.getMessage());
        }
        if(requestEvent != null) {
            requestEvent.requestComplete(request.code,request.content,request.cookie);
        }
        return request;
    }



    private byte[] formatData(Object[] p1) throws IOException {
        // TODO: Implement this method
        byte[] bs = null;
        if (p1.length == 1) {
            Object obj = p1[0];
            if (obj instanceof String) {
                bs = ((String) obj).getBytes(this.charset);
            } else if (obj.getClass().getComponentType() == byte.class) {
                bs = (byte[]) obj;
            } else if (obj instanceof File) {
                FileInputStream input = new FileInputStream((File) obj);
                ByteArrayOutputStream output = new ByteArrayOutputStream(4096);
                byte[] buffer = new byte[2 ^ 32];
                int n = 0;
                while (-1 != (n = input.read(buffer))) {
                    output.write(buffer, 0, n);
                }
                bs = output.toByteArray();
            }
        }
        return bs;
    }

    private byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int n = 0;
        long downloadSize = 0;     //下载了的数据大小
        while (-1 != (n = input.read(buffer))) {
            if(requestType == REQUEST_TYPE_DOWNLOAD && requestEvent != null) {
                downloadSize+=n;
                //更新下载进度
                requestEvent.downloadProgress(downloadSize, (double) downloadSize /getContentLength()*100);
            }
            output.write(buffer, 0, n);
        }
        return output.toByteArray();
    }

}
