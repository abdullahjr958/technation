// /static/js/alert.js

let alertCounter = 0;

function closeAlert(alertId) {
    const alert = document.getElementById(alertId);
    if (alert) {
        alert.classList.add('fade-out');
        setTimeout(() => {
            alert.style.display = 'none';
        }, 300);
    }
}

function showAlert(type, message) {

    const messages = {
        success: {
            title: 'Success!'
        },
        error: {
            title: 'Error'
        },
        info: {
            title: 'Information'
        }
    };

    const alertId = `demo-alert-${++alertCounter}`;
    const demoArea = document.getElementById('demo-area');

    const alertHTML = `
      <div class="alert alert-${type}" id="${alertId}" style="margin-bottom: 1rem;">
        <div class="alert-header">
          <div class="alert-icon"></div>
          <h3 class="alert-title">${messages[type].title}</h3>
        </div>
        <p class="alert-message">${message}</p>
        <button class="alert-close" onclick="closeAlert('${alertId}')">&times;</button>
      </div>
    `;

    demoArea.insertAdjacentHTML('beforeend', alertHTML);

    setTimeout(() => {
        closeAlert(alertId);
    }, 5000);
}
