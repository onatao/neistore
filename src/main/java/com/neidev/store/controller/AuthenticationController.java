package com.neidev.store.controller;

import com.neidev.store.domain.core.user.json.buyer.BuyerRegisterForm;
import com.neidev.store.domain.core.user.json.buyer.BuyerResponse;
import com.neidev.store.domain.core.user.json.seller.SellerRegisterForm;
import com.neidev.store.domain.core.user.model.Buyer;
import com.neidev.store.domain.core.user.model.Seller;
import com.neidev.store.domain.core.user.model.User;
import com.neidev.store.domain.repository.BuyerRepository;
import com.neidev.store.domain.repository.SellerRepository;
import com.neidev.store.domain.repository.UserRepository;
import com.neidev.store.security.infra.service.JwtTokenService;
import com.neidev.store.security.record.LoginRecord;
import com.neidev.store.security.record.LoginResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthenticationController {

    private AuthenticationManager manager;
    private JwtTokenService tokenService;
    private SellerRepository sellerRepository;
    private BuyerRepository buyerRepository;

    public AuthenticationController(AuthenticationManager manager, JwtTokenService tokenService,
                                    SellerRepository sellerRepository, BuyerRepository buyerRepository) {
        this.manager = manager;
        this.tokenService = tokenService;
        this.sellerRepository = sellerRepository;
        this.buyerRepository = buyerRepository;

    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRecord data) {
        var usernamePasswordToken = new UsernamePasswordAuthenticationToken(data.username(), data.password());
        var auth = manager.authenticate(usernamePasswordToken);
        var token = tokenService.generateToken((User) auth.getPrincipal());
        return ResponseEntity.ok().body(new LoginResponse(token));
    }

    @PostMapping("/auth/seller/register")
    public ResponseEntity registerSeller(@RequestBody SellerRegisterForm data) {
        if(sellerRepository.findByCnpj(data.getCnpj()).isPresent())
            return ResponseEntity.badRequest().body("CNPJ already in use.");

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.getPassword());
        Seller s = data.toEntity();
        s.setPassword(encryptedPassword);

        sellerRepository.save(s);
        return ResponseEntity.ok().body("Seller registered");
    }

    @PostMapping("/auth/buyer/register")
    public ResponseEntity registerBuyer(@RequestBody BuyerRegisterForm data) {
        if (buyerRepository.findByCpf(data.getCpf()).isPresent())
            return ResponseEntity.badRequest().body("CPF already registered!");

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.getPassword());
        Buyer b = data.toEntity();
        b.setPassword(encryptedPassword);

        buyerRepository.save(b);
        return ResponseEntity.ok().build();
    }




}
