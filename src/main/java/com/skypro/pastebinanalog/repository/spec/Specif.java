package com.skypro.pastebinanalog.repository.spec;

import com.skypro.pastebinanalog.enums.Status;
import com.skypro.pastebinanalog.model.Pasta;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class Specif {


    public static Specification<Pasta> byTitle(String title){
        return StringUtils.hasText(title) ? (root, query, criteriaBuilder) ->
                criteriaBuilder.and(
                        criteriaBuilder.equal(root.get("title"), title),
                        criteriaBuilder.equal(root.get("status"), Status.PUBLIC)
                ) : null;
    }

    public static Specification<Pasta> byBody(String body){
            return StringUtils.hasText(body) ? (root, query, criteriaBuilder) ->
                    criteriaBuilder.and(
                            criteriaBuilder.like(root.get("body"), "%" + body + "%"),
                            criteriaBuilder.equal(root.get("status"), Status.PUBLIC)
                    ) : null;
        }

}
