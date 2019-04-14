package demo.service;

import act.controller.Controller;
import act.controller.annotation.UrlContext;
import org.osgl.aaa.NoAuthentication;

@NoAuthentication
@UrlContext("/api/v1")
public abstract class PublicServiceBase extends Controller.Util {
}
