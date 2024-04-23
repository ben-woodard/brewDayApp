const open = document.getElementById('modal-editbatch-open');
const modalContainer = document.getElementById('edit-batch-modal-container');
const create= document.getElementById('create-modal-close');
const close = document.getElementById('close-create-modal');
const createTurnButton = document.getElementById('add-turn-open');
const createTurnModal = document.getElementById('create-turn-modal-container');
const submitTurnModal = document.getElementById('create-turn-modal-close');
const closeTurnModal = document.getElementById('close-create-turn-modal');

createTurnButton.addEventListener('click', () => {
    createTurnModal.classList.add('show');
})

submitTurnModal.addEventListener('click', () => {
    createTurnModal.classList.remove('show');
});

closeTurnModal.addEventListener('click', () => {
    createTurnModal.classList.remove('show');
});

open.addEventListener('click', () => {
    modalContainer.classList.add('show');
});

create.addEventListener('click', () => {
    modalContainer.classList.remove('show');
});

close.addEventListener('click', () => {
    modalContainer.classList.remove('show');
});