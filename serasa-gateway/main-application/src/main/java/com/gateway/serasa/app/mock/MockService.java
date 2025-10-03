package com.gateway.serasa.app.mock;

import com.gateway.serasa.common.entity.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MockService {

    private final MockDataLoader loader;

    public MockService(MockDataLoader loader) {
        this.loader = loader;
    }

    public Optional<Pessoa> buscarPorDocumento(String documento) {
        return loader.buscarPorDocumento(documento);
    }

}