const productOpen = document.getElementById('create-product-open')
const modalContainer = document.getElementById('create-modal-container');
const create = document.getElementById('create-modal-close');
const close = document.getElementById('close-create-modal')


productOpen.addEventListener('click', () => {
    modalContainer.classList.add('show');
});

create.addEventListener('click', () => {
    modalContainer.classList.remove('show');
});

close.addEventListener('click', () => {
    modalContainer.classList.remove('show');
});