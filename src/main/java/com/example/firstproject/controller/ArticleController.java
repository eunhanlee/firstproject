package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;

@Slf4j
@Controller
public class ArticleController {
    @Autowired
    private ArticleRepository articleRepository;

    @GetMapping("/article/new")
    public String newArticleForm() {
        return "article/new";
    }

    @PostMapping("/article/create")
    public String createArticle(ArticleForm form) {
        log.info(form.toString());
        Article article = form.toEntity();
        log.info(article.toString());
        Article saved = articleRepository.save(article);
        log.info(saved.toString());
        return "redirect:/article/"+saved.getId();
    }

    @GetMapping("/article/{id}")
    public String show(@PathVariable Long id, Model model) {
        log.info("id = " + id);
//        Optional<Article> articleEntity = articleRepository.findById(id);
        Article articleEntity = articleRepository.findById(id).orElse(null);
        model.addAttribute("article", articleEntity);

        return "article/show";
    }

    @GetMapping("/article")
    public String index(Model model) {

        // findAll() will return Iterable. 3 ways to fix.
//        1. upcasting
//        Iterable<Article> articleEntityList = articleRepository.findAll();
//        2. downcasting
//        List<Article> articleEntityList = (List<Article>)articleRepository.findAll();
//        3. override the findAll method in ArticleRepository Interface.
        ArrayList<Article> articleEntityList = articleRepository.findAll();
        model.addAttribute("articleList", articleEntityList);
        return "article/index";
    }


}
