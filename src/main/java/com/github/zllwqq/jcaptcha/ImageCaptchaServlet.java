package com.github.zllwqq.jcaptcha;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 验证码图片生成
 * @author HNBY
 *
 */
public class ImageCaptchaServlet extends HttpServlet {

	private static final long serialVersionUID = 7331047559155454751L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

		//设置jpg图片格式
		response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        
        String id = request.getRequestedSessionId();
        BufferedImage bi = JCaptcha.captchaService.getImageChallengeForID(id);

        ServletOutputStream out = response.getOutputStream();

        ImageIO.write(bi, "jpg", out);
        
        out.flush();
        out.close();
	}
}
