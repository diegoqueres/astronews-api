let comps = null;
function loadPageComps() {
  comps = {
    title: document.querySelector('#status-title'),
    description: document.querySelector('#status-description'),
  }
}

function showAPISuccessMessage() {
  comps.title.style.color = "green"
  comps.description.style.color = "green"
  comps.description.innerHTML = "Api rodando normalmente! =D"
}

function showAPIErrorMessage(error, isApiWithProblems) {
  comps.title.style.color = "red"
  comps.description.style.color = "red"
  if (isApiWithProblems) {
    comps.description.innerHTML = error.message
  } else {
    comps.description.innerHTML = "Erro ao checar status da API! :'-/"
  }
}

document.addEventListener("DOMContentLoaded", function (event) {
  loadPageComps();

  fetch("/actuator/health", {
    method: 'get',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    }
  }).then(async (res) => {
    const resBody = await res.json()
    if (res.ok && resBody !== null && resBody['status'] === 'UP') {
      showAPISuccessMessage()
    } else {
      showAPIErrorMessage(new Error("API fora do ar! :'-("), true)
      console.log(`Api health is bad. Returned status ${res.status} when fetch endpoint.`)
    }
  }).catch((error) => {
    showAPIErrorMessage(error);
    console.error(error)
  });

});