function includeHTML() {
    const elements = document.querySelectorAll('[data-include]');
  
    elements.forEach(el => {
      const file = el.getAttribute('data-include');
      fetch(file)
        .then(response => {
          if (!response.ok) throw new Error('File not found');
          return response.text();
        })
        .then(data => el.innerHTML = data)
        .catch(error => console.error('Error loading component:', error));
    });
  }
  
  // Run after DOM loads
  document.addEventListener('DOMContentLoaded', includeHTML);





// Appearing and disappearing of Navbar
  function includeHTML(callback) {
    const elements = document.querySelectorAll('[data-include]');
    let loadedCount = 0;
  
    if (elements.length === 0) {
      if (callback) callback();
      return;
    }
  
    elements.forEach(el => {
      const file = el.getAttribute('data-include');
      fetch(file)
        .then(response => {
          if (!response.ok) throw new Error('File not found');
          return response.text();
        })
        .then(data => {
          el.innerHTML = data;
          loadedCount++;
          if (loadedCount === elements.length && callback) {
            callback();
          }
        })
        .catch(error => console.error('Error loading component:', error));
    });
  }
  
  // Attach scroll behavior AFTER includes are done
  document.addEventListener('DOMContentLoaded', function () {
    includeHTML(function () {
      let lastScrollTop = 0;
      const navbar = document.getElementById("mainNavbar");
  
      if (!navbar) {
        console.warn("Navbar element not found after include.");
        return;
      }
  
      window.addEventListener("scroll", function () {
        const currentScroll = window.pageYOffset || document.documentElement.scrollTop;
  
        if (currentScroll > lastScrollTop && currentScroll > 50) {
          // Scrolling down
          navbar.classList.add("navbar-hidden");
        } else {
          // Scrolling up
          navbar.classList.remove("navbar-hidden");
        }
  
        lastScrollTop = currentScroll <= 0 ? 0 : currentScroll;
      });
    });
  });






  // Show search icon on scroll
  window.addEventListener("scroll", () => {
    const icon = document.getElementById("searchIcon");
    if (window.scrollY > 100) {
      icon.classList.remove("hidden");
    } else {
      icon.classList.add("hidden");
    }
  });


