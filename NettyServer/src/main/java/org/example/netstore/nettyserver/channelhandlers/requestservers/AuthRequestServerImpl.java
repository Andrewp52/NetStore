package org.example.netstore.nettyserver.channelhandlers.requestservers;

import org.example.netstore.common.dto.UserDto;
import org.example.netstore.common.protocol.exceptions.auth.AuthException;
import org.example.netstore.common.protocol.requests.Request;
import org.example.netstore.common.protocol.requests.auth.ChangePasswordRequest;
import org.example.netstore.common.protocol.requests.auth.LoginRequest;
import org.example.netstore.common.protocol.requests.auth.UpdateProfileRequest;
import org.example.netstore.common.protocol.responses.Response;
import org.example.netstore.common.protocol.responses.auth.ChangePasswordResponse;
import org.example.netstore.common.protocol.responses.auth.LoginResponse;
import org.example.netstore.common.protocol.responses.auth.ProfileActionResponse;
import org.example.netstore.nettyserver.User;
import org.example.netstore.nettyserver.services.auth.AuthService;

public class AuthRequestServerImpl extends AbstractRequestServer {
    private final AuthService service;

    public AuthRequestServerImpl(AuthService service) {
        this.service = service;
    }

    @Override
    public Response serve(Request request) {
        return super.functionMap.get(request.getClass()).apply(request);
    }

    Response serveReq(LoginRequest request){
        User user = service.logIn(request.getUsername(), request.getPassword());
        if(user != null){
            return new LoginResponse(getUserDto(user), request.getUUID());
        }
        throw new AuthException("Login failed", request.getUUID());
    }

    Response serveReq(ChangePasswordRequest request){
        if(service.changePassword(request.getOldPassword(), request.getNewPassword())){
            return new ChangePasswordResponse(request.getUUID());
        }
        throw new AuthException("Change password failed", request.getUUID());
    }

    Response serveReq(UpdateProfileRequest request){
        User user = service.updateProfile(request.getDto());
        if(user != null){
            return new ProfileActionResponse(getUserDto(user), request.getUUID());
        }
        throw new AuthException("Profile update failed", request.getUUID());
    }

    UserDto getUserDto(User user) {
        return new UserDto(user.getId(),
                user.getUsername(),
                user.isQuotaEnabled(),
                user.getQuota(),
                user.getQuotaRemains()
        );
    }


}
