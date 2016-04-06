package com.wex.poc.repository.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.wex.poc.model.PermissionSetPermission;
import com.wex.poc.model.User;
import com.wex.poc.model.UserPermission;
import com.wex.poc.repository.UserRepository;

@Repository("userRepository")
public class UserRepositoryImpl implements UserRepository {

	@Autowired
	private SessionFactory sessionFactory;

	public User getUserByUserName(String userName) {

		User user = (User) sessionFactory.getCurrentSession()
				.createCriteria(User.class)
				.add(Restrictions.eq("userName", userName)).uniqueResult();
		/*try {
			doIndex();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
*/
		return user;
	}

	@SuppressWarnings("unchecked")
	public List<PermissionSetPermission> getAuthoritiesByUserName(
			String userName) {

		UserPermission permissionSet = (UserPermission) sessionFactory
				.getCurrentSession().createCriteria(UserPermission.class)
				.createAlias("user", "u")
				.add(Restrictions.eq("u.userName", userName)).uniqueResult();
		List<PermissionSetPermission> permissions = sessionFactory
				.getCurrentSession()
				.createCriteria(PermissionSetPermission.class)
				.createAlias("permissionSet", "ps")
				.add(Restrictions.eq("ps.id", permissionSet.getPermissionSet()
						.getId())).list();
		return permissions;
	}

	private void doIndex() throws InterruptedException {

		FullTextSession fullTextSession = Search
				.getFullTextSession(sessionFactory.getCurrentSession());
		fullTextSession.createIndexer().startAndWait();

		fullTextSession.close();
	}
}
