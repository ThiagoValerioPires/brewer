package com.algaworks.brewer.repository.helper.estilo;

import com.algaworks.brewer.model.Estilo;
import com.algaworks.brewer.repository.filter.EstiloFilter;
import com.algaworks.brewer.repository.paginacao.PaginacaoUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class EstiloRepositoryImpl  implements EstiloQueries {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private PaginacaoUtil paginacaoUtil;


    @Override
    @Transactional(readOnly = true)
    public Page<Estilo> filtar(EstiloFilter filtro, Pageable pageable) {

        Criteria criteria = manager.unwrap(Session.class).createCriteria(Estilo.class);

        paginacaoUtil.preparar(criteria,pageable);

        adicionarFiltro(filtro, criteria);

        return new PageImpl<>(criteria.list(),pageable, total(filtro));
    }

    private void adicionarFiltro(EstiloFilter filtro, Criteria criteria) {
        if(filtro != null){
            if (!StringUtils.isEmpty(filtro.getNome())) {
                criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
            }
        }
    }

    private Long total(EstiloFilter filtro) {
        Criteria criteria = manager.unwrap(Session.class).createCriteria(Estilo.class);
        adicionarFiltro(filtro , criteria);
        criteria.setProjection(Projections.rowCount());
        return (Long) criteria.uniqueResult();
    }

}
