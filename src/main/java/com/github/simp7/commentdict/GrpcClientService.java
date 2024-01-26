package com.github.simp7.commentdict;

import com.github.simp7.idl.auth.*;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class GrpcClientService {
    @GrpcClient("auth")
    private AuthGrpc.AuthBlockingStub stub;
    public String login(final String email, String password) {
        TraditionalLoginRequest traditional =  TraditionalLoginRequest.newBuilder().setEmail(email).setPassword(password).build();
        LoginRequest request = LoginRequest.newBuilder().setTraditional(traditional).build();
        return stub.login(request).getToken();
    }
}
