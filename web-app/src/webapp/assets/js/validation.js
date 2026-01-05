function showInlineError(el, msg) {
    let parent = el.closest('.form-row');
    if (!parent) parent = el.parentElement;
    let existing = parent.querySelector('.inline-error');
    if (!existing) {
        existing = document.createElement('div');
        existing.className = 'inline-error error';
        parent.appendChild(existing);
    }
    existing.textContent = msg;
}

function clearInlineError(el) {
    let parent = el.closest('.form-row') || el.parentElement;
    let existing = parent.querySelector('.inline-error');
    if (existing) existing.textContent = '';
}

function validateEmail(email) {
    const emailRegex = /^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/;
    return emailRegex.test(email);
}

function validateLoginForm() {
    const emailEl = document.getElementById('email');
    const passEl = document.getElementById('password');
    let ok = true;
    clearInlineError(emailEl);
    clearInlineError(passEl);

    const email = emailEl.value.trim();
    if (!validateEmail(email)) {
        showInlineError(emailEl, 'Please enter a valid email address.');
        ok = false;
    }
    const password = passEl.value;
    if (!password || password.length < 6) {
        showInlineError(passEl, 'Password must be at least 6 characters.');
        ok = false;
    }
    return ok;
}

document.addEventListener('DOMContentLoaded', function () {
    const form = document.querySelector('form[data-validate="login"]');
    if (form) form.onsubmit = function () { return validateLoginForm(); };
});