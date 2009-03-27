/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portlet.polls.service.persistence;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.annotation.BeanReference;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.polls.NoSuchChoiceException;
import com.liferay.portlet.polls.model.PollsChoice;
import com.liferay.portlet.polls.model.impl.PollsChoiceImpl;
import com.liferay.portlet.polls.model.impl.PollsChoiceModelImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="PollsChoicePersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class PollsChoicePersistenceImpl extends BasePersistenceImpl
	implements PollsChoicePersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = PollsChoice.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = PollsChoice.class.getName() +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_UUID = new FinderPath(PollsChoiceModelImpl.ENTITY_CACHE_ENABLED,
			PollsChoiceModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByUuid", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_UUID = new FinderPath(PollsChoiceModelImpl.ENTITY_CACHE_ENABLED,
			PollsChoiceModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(PollsChoiceModelImpl.ENTITY_CACHE_ENABLED,
			PollsChoiceModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByUuid", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_QUESTIONID = new FinderPath(PollsChoiceModelImpl.ENTITY_CACHE_ENABLED,
			PollsChoiceModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByQuestionId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_QUESTIONID = new FinderPath(PollsChoiceModelImpl.ENTITY_CACHE_ENABLED,
			PollsChoiceModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByQuestionId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_QUESTIONID = new FinderPath(PollsChoiceModelImpl.ENTITY_CACHE_ENABLED,
			PollsChoiceModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByQuestionId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_Q_N = new FinderPath(PollsChoiceModelImpl.ENTITY_CACHE_ENABLED,
			PollsChoiceModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByQ_N",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_Q_N = new FinderPath(PollsChoiceModelImpl.ENTITY_CACHE_ENABLED,
			PollsChoiceModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByQ_N",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(PollsChoiceModelImpl.ENTITY_CACHE_ENABLED,
			PollsChoiceModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(PollsChoiceModelImpl.ENTITY_CACHE_ENABLED,
			PollsChoiceModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countAll", new String[0]);

	public void cacheResult(PollsChoice pollsChoice) {
		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_Q_N,
			new Object[] {
				new Long(pollsChoice.getQuestionId()),
				
			pollsChoice.getName()
			}, pollsChoice);

		EntityCacheUtil.putResult(PollsChoiceModelImpl.ENTITY_CACHE_ENABLED,
			PollsChoice.class, pollsChoice.getPrimaryKey(), pollsChoice);
	}

	public void cacheResult(List<PollsChoice> pollsChoices) {
		for (PollsChoice pollsChoice : pollsChoices) {
			if (EntityCacheUtil.getResult(
						PollsChoiceModelImpl.ENTITY_CACHE_ENABLED,
						PollsChoice.class, pollsChoice.getPrimaryKey(), this) == null) {
				cacheResult(pollsChoice);
			}
		}
	}

	public PollsChoice create(long choiceId) {
		PollsChoice pollsChoice = new PollsChoiceImpl();

		pollsChoice.setNew(true);
		pollsChoice.setPrimaryKey(choiceId);

		String uuid = PortalUUIDUtil.generate();

		pollsChoice.setUuid(uuid);

		return pollsChoice;
	}

	public PollsChoice remove(long choiceId)
		throws NoSuchChoiceException, SystemException {
		Session session = null;

		try {
			session = openSession();

			PollsChoice pollsChoice = (PollsChoice)session.get(PollsChoiceImpl.class,
					new Long(choiceId));

			if (pollsChoice == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No PollsChoice exists with the primary key " +
						choiceId);
				}

				throw new NoSuchChoiceException(
					"No PollsChoice exists with the primary key " + choiceId);
			}

			return remove(pollsChoice);
		}
		catch (NoSuchChoiceException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public PollsChoice remove(PollsChoice pollsChoice)
		throws SystemException {
		for (ModelListener<PollsChoice> listener : listeners) {
			listener.onBeforeRemove(pollsChoice);
		}

		pollsChoice = removeImpl(pollsChoice);

		for (ModelListener<PollsChoice> listener : listeners) {
			listener.onAfterRemove(pollsChoice);
		}

		return pollsChoice;
	}

	protected PollsChoice removeImpl(PollsChoice pollsChoice)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(PollsChoiceImpl.class,
						pollsChoice.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(pollsChoice);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		PollsChoiceModelImpl pollsChoiceModelImpl = (PollsChoiceModelImpl)pollsChoice;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_Q_N,
			new Object[] {
				new Long(pollsChoiceModelImpl.getOriginalQuestionId()),
				
			pollsChoiceModelImpl.getOriginalName()
			});

		EntityCacheUtil.removeResult(PollsChoiceModelImpl.ENTITY_CACHE_ENABLED,
			PollsChoice.class, pollsChoice.getPrimaryKey());

		return pollsChoice;
	}

	/**
	 * @deprecated Use <code>update(PollsChoice pollsChoice, boolean merge)</code>.
	 */
	public PollsChoice update(PollsChoice pollsChoice)
		throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(PollsChoice pollsChoice) method. Use update(PollsChoice pollsChoice, boolean merge) instead.");
		}

		return update(pollsChoice, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        pollsChoice the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when pollsChoice is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public PollsChoice update(PollsChoice pollsChoice, boolean merge)
		throws SystemException {
		boolean isNew = pollsChoice.isNew();

		for (ModelListener<PollsChoice> listener : listeners) {
			if (isNew) {
				listener.onBeforeCreate(pollsChoice);
			}
			else {
				listener.onBeforeUpdate(pollsChoice);
			}
		}

		pollsChoice = updateImpl(pollsChoice, merge);

		for (ModelListener<PollsChoice> listener : listeners) {
			if (isNew) {
				listener.onAfterCreate(pollsChoice);
			}
			else {
				listener.onAfterUpdate(pollsChoice);
			}
		}

		return pollsChoice;
	}

	public PollsChoice updateImpl(
		com.liferay.portlet.polls.model.PollsChoice pollsChoice, boolean merge)
		throws SystemException {
		boolean isNew = pollsChoice.isNew();

		if (Validator.isNull(pollsChoice.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			pollsChoice.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, pollsChoice, merge);

			pollsChoice.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		PollsChoiceModelImpl pollsChoiceModelImpl = (PollsChoiceModelImpl)pollsChoice;

		if (!isNew &&
				((pollsChoice.getQuestionId() != pollsChoiceModelImpl.getOriginalQuestionId()) ||
				!pollsChoice.getName()
								.equals(pollsChoiceModelImpl.getOriginalName()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_Q_N,
				new Object[] {
					new Long(pollsChoiceModelImpl.getOriginalQuestionId()),
					
				pollsChoiceModelImpl.getOriginalName()
				});
		}

		if (isNew ||
				((pollsChoice.getQuestionId() != pollsChoiceModelImpl.getOriginalQuestionId()) ||
				!pollsChoice.getName()
								.equals(pollsChoiceModelImpl.getOriginalName()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_Q_N,
				new Object[] {
					new Long(pollsChoice.getQuestionId()),
					
				pollsChoice.getName()
				}, pollsChoice);
		}

		EntityCacheUtil.putResult(PollsChoiceModelImpl.ENTITY_CACHE_ENABLED,
			PollsChoice.class, pollsChoice.getPrimaryKey(), pollsChoice);

		return pollsChoice;
	}

	public PollsChoice findByPrimaryKey(long choiceId)
		throws NoSuchChoiceException, SystemException {
		PollsChoice pollsChoice = fetchByPrimaryKey(choiceId);

		if (pollsChoice == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No PollsChoice exists with the primary key " +
					choiceId);
			}

			throw new NoSuchChoiceException(
				"No PollsChoice exists with the primary key " + choiceId);
		}

		return pollsChoice;
	}

	public PollsChoice fetchByPrimaryKey(long choiceId)
		throws SystemException {
		PollsChoice result = (PollsChoice)EntityCacheUtil.getResult(PollsChoiceModelImpl.ENTITY_CACHE_ENABLED,
				PollsChoice.class, choiceId, this);

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				PollsChoice pollsChoice = (PollsChoice)session.get(PollsChoiceImpl.class,
						new Long(choiceId));

				if (pollsChoice != null) {
					cacheResult(pollsChoice);
				}

				return pollsChoice;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (PollsChoice)result;
		}
	}

	public List<PollsChoice> findByUuid(String uuid) throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		Object result = FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_UUID,
				finderArgs, this);

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.polls.model.PollsChoice WHERE ");

				if (uuid == null) {
					query.append("uuid_ IS NULL");
				}
				else {
					query.append("uuid_ = ?");
				}

				query.append(" ");

				query.append("ORDER BY ");

				query.append("questionId ASC, ");
				query.append("name ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				List<PollsChoice> list = q.list();

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_UUID, finderArgs,
					list);

				cacheResult(list);

				return list;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<PollsChoice>)result;
		}
	}

	public List<PollsChoice> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	public List<PollsChoice> findByUuid(String uuid, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				uuid,
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		Object result = FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_UUID,
				finderArgs, this);

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.polls.model.PollsChoice WHERE ");

				if (uuid == null) {
					query.append("uuid_ IS NULL");
				}
				else {
					query.append("uuid_ = ?");
				}

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("questionId ASC, ");
					query.append("name ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				List<PollsChoice> list = (List<PollsChoice>)QueryUtil.list(q,
						getDialect(), start, end);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_UUID,
					finderArgs, list);

				cacheResult(list);

				return list;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<PollsChoice>)result;
		}
	}

	public PollsChoice findByUuid_First(String uuid, OrderByComparator obc)
		throws NoSuchChoiceException, SystemException {
		List<PollsChoice> list = findByUuid(uuid, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No PollsChoice exists with the key {");

			msg.append("uuid=" + uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchChoiceException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public PollsChoice findByUuid_Last(String uuid, OrderByComparator obc)
		throws NoSuchChoiceException, SystemException {
		int count = countByUuid(uuid);

		List<PollsChoice> list = findByUuid(uuid, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No PollsChoice exists with the key {");

			msg.append("uuid=" + uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchChoiceException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public PollsChoice[] findByUuid_PrevAndNext(long choiceId, String uuid,
		OrderByComparator obc) throws NoSuchChoiceException, SystemException {
		PollsChoice pollsChoice = findByPrimaryKey(choiceId);

		int count = countByUuid(uuid);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.polls.model.PollsChoice WHERE ");

			if (uuid == null) {
				query.append("uuid_ IS NULL");
			}
			else {
				query.append("uuid_ = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("questionId ASC, ");
				query.append("name ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			if (uuid != null) {
				qPos.add(uuid);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					pollsChoice);

			PollsChoice[] array = new PollsChoiceImpl[3];

			array[0] = (PollsChoice)objArray[0];
			array[1] = (PollsChoice)objArray[1];
			array[2] = (PollsChoice)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<PollsChoice> findByQuestionId(long questionId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(questionId) };

		Object result = FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_QUESTIONID,
				finderArgs, this);

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.polls.model.PollsChoice WHERE ");

				query.append("questionId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("questionId ASC, ");
				query.append("name ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(questionId);

				List<PollsChoice> list = q.list();

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_QUESTIONID,
					finderArgs, list);

				cacheResult(list);

				return list;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<PollsChoice>)result;
		}
	}

	public List<PollsChoice> findByQuestionId(long questionId, int start,
		int end) throws SystemException {
		return findByQuestionId(questionId, start, end, null);
	}

	public List<PollsChoice> findByQuestionId(long questionId, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(questionId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		Object result = FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_QUESTIONID,
				finderArgs, this);

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.polls.model.PollsChoice WHERE ");

				query.append("questionId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("questionId ASC, ");
					query.append("name ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(questionId);

				List<PollsChoice> list = (List<PollsChoice>)QueryUtil.list(q,
						getDialect(), start, end);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_QUESTIONID,
					finderArgs, list);

				cacheResult(list);

				return list;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<PollsChoice>)result;
		}
	}

	public PollsChoice findByQuestionId_First(long questionId,
		OrderByComparator obc) throws NoSuchChoiceException, SystemException {
		List<PollsChoice> list = findByQuestionId(questionId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No PollsChoice exists with the key {");

			msg.append("questionId=" + questionId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchChoiceException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public PollsChoice findByQuestionId_Last(long questionId,
		OrderByComparator obc) throws NoSuchChoiceException, SystemException {
		int count = countByQuestionId(questionId);

		List<PollsChoice> list = findByQuestionId(questionId, count - 1, count,
				obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No PollsChoice exists with the key {");

			msg.append("questionId=" + questionId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchChoiceException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public PollsChoice[] findByQuestionId_PrevAndNext(long choiceId,
		long questionId, OrderByComparator obc)
		throws NoSuchChoiceException, SystemException {
		PollsChoice pollsChoice = findByPrimaryKey(choiceId);

		int count = countByQuestionId(questionId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.polls.model.PollsChoice WHERE ");

			query.append("questionId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("questionId ASC, ");
				query.append("name ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(questionId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					pollsChoice);

			PollsChoice[] array = new PollsChoiceImpl[3];

			array[0] = (PollsChoice)objArray[0];
			array[1] = (PollsChoice)objArray[1];
			array[2] = (PollsChoice)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public PollsChoice findByQ_N(long questionId, String name)
		throws NoSuchChoiceException, SystemException {
		PollsChoice pollsChoice = fetchByQ_N(questionId, name);

		if (pollsChoice == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No PollsChoice exists with the key {");

			msg.append("questionId=" + questionId);

			msg.append(", ");
			msg.append("name=" + name);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchChoiceException(msg.toString());
		}

		return pollsChoice;
	}

	public PollsChoice fetchByQ_N(long questionId, String name)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(questionId), name };

		Object result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_Q_N,
				finderArgs, this);

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.polls.model.PollsChoice WHERE ");

				query.append("questionId = ?");

				query.append(" AND ");

				if (name == null) {
					query.append("name IS NULL");
				}
				else {
					query.append("name = ?");
				}

				query.append(" ");

				query.append("ORDER BY ");

				query.append("questionId ASC, ");
				query.append("name ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(questionId);

				if (name != null) {
					qPos.add(name);
				}

				List<PollsChoice> list = q.list();

				PollsChoice pollsChoice = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_Q_N,
						finderArgs, list);
				}
				else {
					pollsChoice = list.get(0);

					cacheResult(pollsChoice);
				}

				return pollsChoice;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			if (result instanceof List) {
				return null;
			}
			else {
				return (PollsChoice)result;
			}
		}
	}

	public List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			dynamicQuery.compile(session);

			return dynamicQuery.list();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			dynamicQuery.setLimit(start, end);

			dynamicQuery.compile(session);

			return dynamicQuery.list();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<PollsChoice> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<PollsChoice> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<PollsChoice> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		Object result = FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.polls.model.PollsChoice ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("questionId ASC, ");
					query.append("name ASC");
				}

				Query q = session.createQuery(query.toString());

				List<PollsChoice> list = null;

				if (obc == null) {
					list = (List<PollsChoice>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<PollsChoice>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				cacheResult(list);

				return list;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<PollsChoice>)result;
		}
	}

	public void removeByUuid(String uuid) throws SystemException {
		for (PollsChoice pollsChoice : findByUuid(uuid)) {
			remove(pollsChoice);
		}
	}

	public void removeByQuestionId(long questionId) throws SystemException {
		for (PollsChoice pollsChoice : findByQuestionId(questionId)) {
			remove(pollsChoice);
		}
	}

	public void removeByQ_N(long questionId, String name)
		throws NoSuchChoiceException, SystemException {
		PollsChoice pollsChoice = findByQ_N(questionId, name);

		remove(pollsChoice);
	}

	public void removeAll() throws SystemException {
		for (PollsChoice pollsChoice : findAll()) {
			remove(pollsChoice);
		}
	}

	public int countByUuid(String uuid) throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		Object result = FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID,
				finderArgs, this);

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.polls.model.PollsChoice WHERE ");

				if (uuid == null) {
					query.append("uuid_ IS NULL");
				}
				else {
					query.append("uuid_ = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_UUID,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public int countByQuestionId(long questionId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(questionId) };

		Object result = FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_QUESTIONID,
				finderArgs, this);

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.polls.model.PollsChoice WHERE ");

				query.append("questionId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(questionId);

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_QUESTIONID,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public int countByQ_N(long questionId, String name)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(questionId), name };

		Object result = FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_Q_N,
				finderArgs, this);

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.polls.model.PollsChoice WHERE ");

				query.append("questionId = ?");

				query.append(" AND ");

				if (name == null) {
					query.append("name IS NULL");
				}
				else {
					query.append("name = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(questionId);

				if (name != null) {
					qPos.add(name);
				}

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_Q_N, finderArgs,
					count);

				return count.intValue();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public int countAll() throws SystemException {
		Object[] finderArgs = new Object[0];

		Object result = FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				finderArgs, this);

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(
						"SELECT COUNT(*) FROM com.liferay.portlet.polls.model.PollsChoice");

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_ALL, finderArgs,
					count);

				return count.intValue();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.polls.model.PollsChoice")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<PollsChoice>> listenersList = new ArrayList<ModelListener<PollsChoice>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<PollsChoice>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(name = "com.liferay.portlet.polls.service.persistence.PollsChoicePersistence.impl")
	protected com.liferay.portlet.polls.service.persistence.PollsChoicePersistence pollsChoicePersistence;
	@BeanReference(name = "com.liferay.portlet.polls.service.persistence.PollsQuestionPersistence.impl")
	protected com.liferay.portlet.polls.service.persistence.PollsQuestionPersistence pollsQuestionPersistence;
	@BeanReference(name = "com.liferay.portlet.polls.service.persistence.PollsVotePersistence.impl")
	protected com.liferay.portlet.polls.service.persistence.PollsVotePersistence pollsVotePersistence;
	private static Log _log = LogFactoryUtil.getLog(PollsChoicePersistenceImpl.class);
}