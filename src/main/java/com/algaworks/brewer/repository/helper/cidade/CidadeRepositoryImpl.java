package com.algaworks.brewer.repository.helper.cidade;

import com.algaworks.brewer.model.Cidade;
import com.algaworks.brewer.repository.filter.CidadeFilter;
import com.algaworks.brewer.repository.paginacao.PaginacaoUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class CidadeRepositoryImpl  implements CidadeQueries {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private PaginacaoUtil paginacaoUtil;

    @Override
    @Transactional(readOnly = true)
    public Page<Cidade> filtar(CidadeFilter filtro, Pageable pageable) {

        Criteria criteria = manager.unwrap(Session.class).createCriteria(Cidade.class);

        paginacaoUtil.preparar(criteria, pageable);
        criteria.createAlias("estado","e", JoinType.INNER_JOIN);
        adicionarFiltro(filtro, criteria);

        return new PageImpl<Cidade>(criteria.list(), pageable, total(filtro));
    }

    private void adicionarFiltro(CidadeFilter filtro, Criteria criteria) {
        if(filtro != null){
            if (!StringUtils.isEmpty(filtro.getNome())) {
                criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
            }
            if (filtro.getEstado() != null) {
                criteria.add(Restrictions.eq("estado", filtro.getEstado()));
            }
        }
    }

    private Long total(CidadeFilter filtro) {
        Criteria criteria = manager.unwrap(Session.class).createCriteria(Cidade.class);
        adicionarFiltro(filtro , criteria);
        criteria.setProjection(Projections.rowCount());
        return (Long) criteria.uniqueResult();
    }

}