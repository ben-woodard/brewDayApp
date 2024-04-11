const open = document.getElementById('create-modal-open')
const modalContainer = document.getElementById('create-modal-container');
const create = document.getElementById('create-modal-close');
const close = document.getElementById('close-create-modal')

open.addEventListener('click', () => {
    modalContainer.classList.add('show');
});

create.addEventListener('click', () => {
    modalContainer.classList.remove('show');
});

close.addEventListener('click', () => {
    modalContainer.classList.remove('show');
});