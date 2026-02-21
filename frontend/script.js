const API_BASE = 'http://localhost:9081/api';
const DEFAULT_USER_ID = 1;

const CATEGORY_LABELS = {
    PLANIFICATION: 'Planificación',
    PROGRESS: 'Progreso',
    REFLECTION: 'Reflexión',
    GENERAL: 'General',
    OTHER: 'Otro'
};

let selectedPlanId = null;
let selectedPostId = null;
let selectedCategory = null;
let plans = [];
let actions = [];
let posts = [];
let comments = [];
let categories = [];

const elements = {
    sectionPlans: document.getElementById('sectionPlans'),
    sectionForum: document.getElementById('sectionForum'),
    navTabs: document.querySelectorAll('.nav-tab'),
    planList: document.getElementById('planList'),
    detailPlaceholder: document.getElementById('detailPlaceholder'),
    detailContent: document.getElementById('detailContent'),
    detailPlanTitle: document.getElementById('detailPlanTitle'),
    detailPlanDescription: document.getElementById('detailPlanDescription'),
    actionList: document.getElementById('actionList'),
    btnNewPlan: document.getElementById('btnNewPlan'),
    btnNewAction: document.getElementById('btnNewAction'),
    btnDeletePlan: document.getElementById('btnDeletePlan'),
    postList: document.getElementById('postList'),
    categoriesView: document.getElementById('categoriesView'),
    btnBackToCategories: document.getElementById('btnBackToCategories'),
    forumTitle: document.getElementById('forumTitle'),
    forumPlaceholder: document.getElementById('forumPlaceholder'),
    forumContent: document.getElementById('forumContent'),
    postTitle: document.getElementById('postTitle'),
    postCategory: document.getElementById('postCategory'),
    postMeta: document.getElementById('postMeta'),
    postBody: document.getElementById('postBody'),
    postLikeCount: document.getElementById('postLikeCount'),
    btnLike: document.getElementById('btnLike'),
    btnDislike: document.getElementById('btnDislike'),
    commentList: document.getElementById('commentList'),
    formComment: document.getElementById('formComment'),
    commentText: document.getElementById('commentText'),
    btnNewPost: document.getElementById('btnNewPost'),
    modalPlan: document.getElementById('modalPlan'),
    modalAction: document.getElementById('modalAction'),
    modalPost: document.getElementById('modalPost'),
    formPlan: document.getElementById('formPlan'),
    formAction: document.getElementById('formAction'),
    formPost: document.getElementById('formPost'),
    planTitle: document.getElementById('planTitle'),
    planDescription: document.getElementById('planDescription'),
    planStatus: document.getElementById('planStatus'),
    actionTitle: document.getElementById('actionTitle'),
    actionDescription: document.getElementById('actionDescription'),
    actionPlanContext: document.getElementById('actionPlanContext'),
    postTitleInput: document.getElementById('postTitleInput'),
    postContentInput: document.getElementById('postContentInput'),
    postCategorySelect: document.getElementById('postCategorySelect'),
    btnCancelPlan: document.getElementById('btnCancelPlan'),
    btnCancelAction: document.getElementById('btnCancelAction'),
    btnCancelPost: document.getElementById('btnCancelPost'),
    darkModeToggle: document.getElementById('darkModeToggle')
};

async function fetchApi(path, options = {}) {
    const url = `${API_BASE}${path}`;
    const config = {
        headers: { 'Content-Type': 'application/json', ...options.headers },
        ...options
    };
    if (config.body && typeof config.body === 'object' && !(config.body instanceof FormData)) {
        config.body = JSON.stringify(config.body);
    }
    const res = await fetch(url, config);
    if (!res.ok) {
        const text = await res.text();
        throw new Error(text || `Error ${res.status}`);
    }
    if (res.status === 204) return null;
    const ct = res.headers.get('Content-Type');
    return (ct && ct.includes('application/json')) ? res.json() : res.text();
}

function escapeHtml(text) {
    const div = document.createElement('div');
    div.textContent = text ?? '';
    return div.innerHTML;
}

function formatDate(iso) {
    if (!iso) return '';
    const d = new Date(iso);
    return d.toLocaleString('es', { dateStyle: 'short', timeStyle: 'short' });
}

// --- Navegación ---
function switchSection(section) {
    elements.navTabs.forEach(t => {
        t.classList.toggle('active', t.dataset.section === section);
    });
    elements.sectionPlans.classList.toggle('hidden', section !== 'plans');
    elements.sectionForum.classList.toggle('hidden', section !== 'forum');
    if (section === 'plans') loadPlans();
    if (section === 'forum') {
        showCategoriesView();
        loadCategories();
    }
}

elements.navTabs?.forEach(tab => {
    tab.addEventListener('click', () => switchSection(tab.dataset.section));
});

// --- Planes ---
async function loadPlans() {
    try {
        plans = await fetchApi('/plans');
        renderPlanList();
    } catch (err) {
        console.error('Error cargando planes:', err);
        elements.planList.innerHTML = '<li class="list-item muted">No se pudo cargar. ¿Backend en marcha? (puerto 9081)</li>';
    }
}

async function loadActions() {
    try {
        actions = await fetchApi('/actions');
        if (selectedPlanId != null) {
            renderActionList(actions.filter(a => a.planId === selectedPlanId));
        }
    } catch (err) {
        console.error('Error cargando acciones:', err);
        elements.actionList.innerHTML = '<li class="muted">Error al cargar acciones.</li>';
    }
}

function renderPlanList() {
    if (!plans.length) {
        elements.planList.innerHTML = '<li class="list-item muted">No hay planes. Crea uno con "+ Nuevo plan".</li>';
        return;
    }
    elements.planList.innerHTML = plans.map(plan => `
        <li class="list-item ${plan.id === selectedPlanId ? 'active' : ''}" data-plan-id="${plan.id}">
            <h4>${escapeHtml(plan.title || 'Sin título')}</h4>
            <p>${escapeHtml((plan.description || '').slice(0, 60))}${(plan.description || '').length > 60 ? '…' : ''}</p>
        </li>
    `).join('');
    elements.planList.querySelectorAll('.list-item[data-plan-id]').forEach(li => {
        li.addEventListener('click', () => selectPlan(Number(li.dataset.planId)));
    });
}

function renderActionList(list) {
    const items = list || actions.filter(a => a.planId === selectedPlanId);
    if (!items.length) {
        elements.actionList.innerHTML = '<li class="list-item muted">No hay acciones. Añade una con "+ Nueva acción".</li>';
        return;
    }
    elements.actionList.innerHTML = items.map(action => `
        <li class="list-item" data-action-id="${action.id}">
            <div>
                <h4>${escapeHtml(action.title || 'Sin título')}</h4>
                <p>${escapeHtml((action.description || '').slice(0, 80))}${(action.description || '').length > 80 ? '…' : ''}</p>
            </div>
            <div class="item-actions">
                <button type="button" class="btn-danger btn-sm btn-delete-action" data-id="${action.id}">Eliminar</button>
            </div>
        </li>
    `).join('');
    elements.actionList.querySelectorAll('.btn-delete-action').forEach(btn => {
        btn.addEventListener('click', e => {
            e.stopPropagation();
            deleteAction(Number(btn.dataset.id));
        });
    });
}

function selectPlan(planId) {
    selectedPlanId = planId;
    const plan = plans.find(p => p.id === planId);
    if (!plan) {
        elements.detailPlaceholder.classList.remove('hidden');
        elements.detailContent.classList.add('hidden');
        return;
    }
    elements.detailPlaceholder.classList.add('hidden');
    elements.detailContent.classList.remove('hidden');
    elements.detailPlanTitle.textContent = plan.title || 'Sin título';
    elements.detailPlanDescription.textContent = plan.description || '';
    renderPlanList();
    loadActions();
}

function showPlanModal() {
    elements.formPlan.reset();
    elements.planStatus.value = 'ACTIVE';
    elements.modalPlan.querySelector('h3').textContent = 'Nuevo plan';
    elements.modalPlan.showModal();
}

function showActionModal() {
    if (!selectedPlanId) return;
    const plan = plans.find(p => p.id === selectedPlanId);
    elements.formAction.reset();
    elements.actionPlanContext.textContent = plan ? `Plan: ${plan.title || 'Sin título'}` : '';
    elements.modalAction.showModal();
}

async function submitPlan(e) {
    e.preventDefault();
    try {
        await fetchApi('/plans', {
            method: 'POST',
            body: {
                title: elements.planTitle.value.trim(),
                description: elements.planDescription.value.trim(),
                status: elements.planStatus.value,
                actionId: [],
                progressId: [],
                userId: DEFAULT_USER_ID
            }
        });
        elements.modalPlan.close();
        await loadPlans();
    } catch (err) {
        alert('Error al crear el plan: ' + (err.message || err));
    }
}

async function submitAction(e) {
    e.preventDefault();
    if (!selectedPlanId) return;
    try {
        await fetchApi('/actions', {
            method: 'POST',
            body: {
                title: elements.actionTitle.value.trim(),
                description: elements.actionDescription.value.trim(),
                planId: selectedPlanId,
                userId: DEFAULT_USER_ID
            }
        });
        elements.modalAction.close();
        await loadActions();
    } catch (err) {
        alert('Error al crear la acción: ' + (err.message || err));
    }
}

async function deletePlan() {
    if (!selectedPlanId || !confirm('¿Eliminar este plan?')) return;
    try {
        await fetchApi(`/plans/${selectedPlanId}`, { method: 'DELETE' });
        selectedPlanId = null;
        elements.detailPlaceholder.classList.remove('hidden');
        elements.detailContent.classList.add('hidden');
        await loadPlans();
        await loadActions();
    } catch (err) {
        alert('Error al eliminar: ' + (err.message || err));
    }
}

async function deleteAction(id) {
    if (!confirm('¿Eliminar esta acción?')) return;
    try {
        await fetchApi(`/actions/${id}`, { method: 'DELETE' });
        await loadActions();
    } catch (err) {
        alert('Error al eliminar: ' + (err.message || err));
    }
}

// --- Foro ---
async function loadCategories() {
    try {
        categories = await fetchApi('/posts/categories');
        renderCategories();
    } catch (err) {
        console.error('Error cargando categorías:', err);
        elements.categoriesView.innerHTML = '<p class="muted">Error al cargar categorías.</p>';
    }
}

async function loadPosts(category = null) {
    try {
        if (category) {
            posts = await fetchApi(`/posts/category/${category}`);
            selectedCategory = category;
        } else {
            posts = await fetchApi('/posts');
            selectedCategory = null;
        }
        showPostsView();
        renderPostList();
    } catch (err) {
        console.error('Error cargando posts:', err);
        elements.postList.innerHTML = '<li class="list-item muted">No se pudo cargar.</li>';
    }
}

function showCategoriesView() {
    elements.categoriesView.style.display = 'grid';
    elements.postList.style.display = 'none';
    elements.btnBackToCategories.style.display = 'none';
    elements.forumTitle.textContent = 'Foro';
    selectedCategory = null;
    selectedPostId = null;
    elements.forumPlaceholder.classList.remove('hidden');
    elements.forumContent.classList.add('hidden');
}

function showPostsView() {
    elements.categoriesView.style.display = 'none';
    elements.postList.style.display = 'block';
    elements.btnBackToCategories.style.display = 'block';
    const categoryLabel = selectedCategory ? CATEGORY_LABELS[selectedCategory] || selectedCategory : 'Todos';
    elements.forumTitle.textContent = `Foro - ${categoryLabel}`;
}

function renderCategories() {
    if (!categories.length) {
        elements.categoriesView.innerHTML = '<p class="muted">No hay categorías disponibles.</p>';
        return;
    }
    elements.categoriesView.innerHTML = categories.map(cat => `
        <div class="category-card" data-category="${cat}">
            <h3>${CATEGORY_LABELS[cat] || cat}</h3>
            <p class="muted">Ver posts de esta categoría</p>
        </div>
    `).join('');
    elements.categoriesView.querySelectorAll('.category-card').forEach(card => {
        card.addEventListener('click', () => {
            loadPosts(card.dataset.category);
        });
    });
}

async function loadComments(postId) {
    try {
        comments = await fetchApi(`/posts/${postId}/comments`);
        renderCommentList();
    } catch (err) {
        console.error('Error cargando comentarios:', err);
        elements.commentList.innerHTML = '<li class="muted">Error al cargar comentarios.</li>';
    }
}

function renderPostList() {
    if (!posts.length) {
        elements.postList.innerHTML = '<li class="list-item muted">No hay posts. Crea uno con "+ Nuevo post".</li>';
        return;
    }
    elements.postList.innerHTML = posts.map(post => `
        <li class="list-item ${post.id === selectedPostId ? 'active' : ''}" data-post-id="${post.id}">
            <h4>${escapeHtml(post.title || 'Sin título')}</h4>
            <p class="post-preview">${escapeHtml((post.content || '').slice(0, 80))}${(post.content || '').length > 80 ? '…' : ''}</p>
            <p class="muted small">${CATEGORY_LABELS[post.category] || post.category} · ${formatDate(post.createdAt)} · <span class="like-badge">👍 ${post.like ?? 0}</span></p>
        </li>
    `).join('');
    elements.postList.querySelectorAll('.list-item[data-post-id]').forEach(li => {
        li.addEventListener('click', () => selectPost(Number(li.dataset.postId)));
    });
}

function renderCommentList() {
    if (!comments.length) {
        elements.commentList.innerHTML = '<li class="list-item muted">No hay comentarios. ¡Sé el primero!</li>';
        return;
    }
    elements.commentList.innerHTML = comments.map(c => `
        <li class="comment-item">
            <p class="comment-text">${escapeHtml(c.text)}</p>
            <p class="muted small">${formatDate(c.createdAt)}</p>
        </li>
    `).join('');
}

function selectPost(postId) {
    selectedPostId = postId;
    const post = posts.find(p => p.id === postId);
    if (!post) {
        elements.forumPlaceholder.classList.remove('hidden');
        elements.forumContent.classList.add('hidden');
        return;
    }
    elements.forumPlaceholder.classList.add('hidden');
    elements.forumContent.classList.remove('hidden');
    elements.postTitle.textContent = post.title || 'Sin título';
    elements.postCategory.textContent = CATEGORY_LABELS[post.category] || post.category;
    elements.postCategory.className = 'badge badge-' + (post.category || 'GENERAL').toLowerCase();
    elements.postMeta.textContent = formatDate(post.createdAt);
    elements.postBody.textContent = post.content || '';
    elements.postLikeCount.textContent = post.like ?? 0;
    elements.formComment.reset();
    renderPostList();
    loadComments(postId);
}

function showPostModal() {
    elements.formPost.reset();
    elements.modalPost.showModal();
}

async function submitPost(e) {
    e.preventDefault();
    try {
        await fetchApi('/posts', {
            method: 'POST',
            body: {
                title: elements.postTitleInput.value.trim(),
                content: elements.postContentInput.value.trim(),
                category: elements.postCategorySelect.value,
                authorId: DEFAULT_USER_ID,
                like: 0
            }
        });
        elements.modalPost.close();
        if (selectedCategory) {
            await loadPosts(selectedCategory);
        } else {
            showCategoriesView();
            await loadCategories();
        }
    } catch (err) {
        alert('Error al publicar: ' + (err.message || err));
    }
}

async function updatePostLikes(postId, isLike) {
    try {
        const updated = await fetchApi(`/posts/${postId}/likes`, {
            method: 'PATCH',
            body: JSON.stringify(isLike)
        });
        const idx = posts.findIndex(p => p.id === postId);
        if (idx >= 0 && updated) {
            posts[idx] = { ...posts[idx], ...updated };
            renderPostList();
            if (selectedPostId === postId) {
                elements.postLikeCount.textContent = updated.like ?? posts[idx].like ?? 0;
            }
        }
    } catch (err) {
        alert('Error al actualizar likes: ' + (err.message || err));
    }
}

async function submitComment(e) {
    e.preventDefault();
    if (!selectedPostId) return;
    const text = elements.commentText.value.trim();
    if (!text) return;
    try {
        await fetchApi('/comments', {
            method: 'POST',
            body: { text, userId: DEFAULT_USER_ID, postId: selectedPostId }
        });
        elements.formComment.reset();
        await loadComments(selectedPostId);
    } catch (err) {
        alert('Error al comentar: ' + (err.message || err));
    }
}

// --- Eventos ---
elements.btnNewPlan?.addEventListener('click', showPlanModal);
elements.btnNewAction?.addEventListener('click', showActionModal);
elements.btnDeletePlan?.addEventListener('click', deletePlan);
elements.formPlan?.addEventListener('submit', submitPlan);
elements.formAction?.addEventListener('submit', submitAction);
elements.btnCancelPlan?.addEventListener('click', () => elements.modalPlan.close());
elements.btnCancelAction?.addEventListener('click', () => elements.modalAction.close());

elements.btnNewPost?.addEventListener('click', showPostModal);
elements.formPost?.addEventListener('submit', submitPost);
elements.formComment?.addEventListener('submit', submitComment);
elements.btnCancelPost?.addEventListener('click', () => elements.modalPost.close());
elements.btnLike?.addEventListener('click', () => selectedPostId && updatePostLikes(selectedPostId, true));
elements.btnDislike?.addEventListener('click', () => selectedPostId && updatePostLikes(selectedPostId, false));
elements.btnBackToCategories?.addEventListener('click', () => {
    showCategoriesView();
    loadCategories();
});

// --- Dark mode ---
function initDarkMode() {
    const body = document.body;
    if (localStorage.getItem('theme') === 'dark-mode') {
        body.classList.add('dark-mode');
        if (elements.darkModeToggle) elements.darkModeToggle.textContent = 'Modo Claro';
    }
    elements.darkModeToggle?.addEventListener('click', () => {
        body.classList.toggle('dark-mode');
        const isDark = body.classList.contains('dark-mode');
        if (elements.darkModeToggle) elements.darkModeToggle.textContent = isDark ? 'Modo Claro' : 'Modo Oscuro';
        localStorage.setItem('theme', isDark ? 'dark-mode' : 'light-mode');
    });
}

document.addEventListener('DOMContentLoaded', () => {
    initDarkMode();
    loadPlans();
});
