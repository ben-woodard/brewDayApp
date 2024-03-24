const productName = prompt('Please Type In a New Product Name')

const product = {
    "productName": productName.value
}



// const message = {
//     "messageText": messageText.value,
//     "channel": {
//         "channelName": channelName
//     },
//     "user": {
//         "name": name
//     }
// }
// console.log(message)
// fetch('/messages/create', {
//     method: 'POST',
//     headers: {
//         'Content-Type': 'application/json'
//     },
//     body: JSON.stringify(message)
// }).then(response => {
//     console.log(response)
// })
//
// })