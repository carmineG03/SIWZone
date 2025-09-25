package esame.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import esame.service.ProdottoService;

/**
 * Controller Advice globale per rendere disponibili dati comuni a tutte le pagine
 */
@ControllerAdvice
public class GlobalControllerAdvice {

    @Autowired
    private ProdottoService prodottoService;

    /**
     * Rende disponibili tutti i nomi dei prodotti in tutte le pagine per l'autocomplete
     */
    @ModelAttribute("nomiProdotti")
    public List<String> getNomiProdotti() {
        return prodottoService.getAllNomiProdotti();
    }
}
