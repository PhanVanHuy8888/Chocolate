package com.ecom.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
public class SessionServiceImpl implements SessionService{
    @Override
    public void removeSession() {
        HttpServletRequest request = ((ServletRequestAttributes)(RequestContextHolder.getRequestAttributes())).getRequest();
        HttpSession session =  request.getSession();
        session.removeAttribute("errorMsg");
        session.removeAttribute("succMsg");

    }
}
