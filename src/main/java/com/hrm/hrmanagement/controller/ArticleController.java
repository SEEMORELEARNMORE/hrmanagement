package com.hrm.hrmanagement.controller;

import com.hrm.hrmanagement.entity.Article;
import com.hrm.hrmanagement.entity.vo.ArticleVo;
import com.hrm.hrmanagement.entity.vo.TempArticle;
import com.hrm.hrmanagement.mapper.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleMapper articleMapper;

    @PostMapping("/add")
    public Integer insert(@RequestBody Article article){
        articleMapper.insert(article);
        return articleMapper.findId(article);
    }

    @GetMapping("/getlist")
    public List<Article> index(){return articleMapper.getlist();}

    @GetMapping("/getlistwithstate/{id}")
    public List<TempArticle> getlistwithstate(@PathVariable String id){
        List<Integer> ids = articleMapper.getlistwithstate(id);
        System.out.println("ids:"+ids);
        List<Article> articles = articleMapper.getlist();
        List<TempArticle> tempArticles = new ArrayList<TempArticle>();
        Boolean flag;
        for (Article article : articles) {
            flag = false;
            for(Integer temp_id:ids){
                if(article.getId()==temp_id){
                    flag=true;
                }
            }

                TempArticle t = new TempArticle(article.getId(),article.getAuthor(),article.getAuthor_id(),article.getTitle(),article.getBody(),article.getTime(),!flag);
                tempArticles.add(t);
        }
        System.out.println("输出拼接后的list数据："+tempArticles);
        return tempArticles;
    }

    @GetMapping("/getbyid/{id}")
    public Article getbyid(@PathVariable Integer id){return articleMapper.getbyid(id);}

    @PostMapping("/findbycondition")
    public List<Article>  findbycondition(@RequestBody ArticleVo articleVo){
        System.out.println(articleVo);
        return articleMapper.findbycondition(articleVo);
    }
}
