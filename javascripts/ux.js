// JavaMT4 docs: the contact panel (support channels).
document.addEventListener('DOMContentLoaded', function() {
  initContactPanel();
});

// ============================================================================
// CONTACT PANEL - Quick access to support channels
// ============================================================================

function initContactPanel() {
  // Create and inject contact button and panel
  createContactPanel();
}

function createContactPanel() {
  // Check if contact panel already exists
  if (document.getElementById('contact-float-btn')) return;

  const contactHTML = `
    <!-- Floating Contact Button -->
    <button id="contact-float-btn" class="contact-float-btn" title="Contact & Support">
      <svg width="28" height="28" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
        <path d="M21 15a2 2 0 0 1-2 2H7l-4 4V5a2 2 0 0 1 2-2h14a2 2 0 0 1 2 2z"></path>
      </svg>
    </button>

    <!-- Contact Side Panel -->
    <div id="contact-panel" class="contact-panel">
      <div class="contact-panel-header">
        <h3>💬 Contact & Support</h3>
        <button id="contact-panel-close" class="contact-panel-close" title="Close">&times;</button>
      </div>

      <div class="contact-panel-content">
        <div class="contact-intro">
          <h4>Need Help?</h4>
          <p>Have questions about PyMT5 SDK? Reach out to us through your preferred messenger!</p>
        </div>

        <div class="contact-buttons">
          <!-- Telegram Button -->
          <a href="https://t.me/MetaRPC_Support" target="_blank" class="contact-btn contact-btn-telegram">
            <div class="contact-btn-icon">
              <svg width="24" height="24" viewBox="0 0 24 24" fill="currentColor">
                <path d="M12 0C5.373 0 0 5.373 0 12s5.373 12 12 12 12-5.373 12-12S18.627 0 12 0zm5.562 8.161l-1.84 8.673c-.139.622-.502.775-.998.483l-2.764-2.037-1.332 1.282c-.147.147-.271.271-.556.271l.199-2.815 5.139-4.643c.224-.199-.048-.31-.347-.111l-6.355 4.003-2.737-.856c-.595-.187-.607-.595.125-.881l10.703-4.124c.496-.182.93.114.762.877z"/>
              </svg>
            </div>
            <div class="contact-btn-content">
              <div class="contact-btn-title">Telegram</div>
              <div class="contact-btn-desc">Quick responses & community chat</div>
            </div>
          </a>

          <!-- WhatsApp Button -->
          <a href="https://wa.me/79956432506" target="_blank" class="contact-btn contact-btn-whatsapp">
            <div class="contact-btn-icon">
              <svg width="24" height="24" viewBox="0 0 24 24" fill="currentColor">
                <path d="M17.472 14.382c-.297-.149-1.758-.867-2.03-.967-.273-.099-.471-.148-.67.15-.197.297-.767.966-.94 1.164-.173.199-.347.223-.644.075-.297-.15-1.255-.463-2.39-1.475-.883-.788-1.48-1.761-1.653-2.059-.173-.297-.018-.458.13-.606.134-.133.298-.347.446-.52.149-.174.198-.298.298-.497.099-.198.05-.371-.025-.52-.075-.149-.669-1.612-.916-2.207-.242-.579-.487-.5-.669-.51-.173-.008-.371-.01-.57-.01-.198 0-.52.074-.792.372-.272.297-1.04 1.016-1.04 2.479 0 1.462 1.065 2.875 1.213 3.074.149.198 2.096 3.2 5.077 4.487.709.306 1.262.489 1.694.625.712.227 1.36.195 1.871.118.571-.085 1.758-.719 2.006-1.413.248-.694.248-1.289.173-1.413-.074-.124-.272-.198-.57-.347m-5.421 7.403h-.004a9.87 9.87 0 01-5.031-1.378l-.361-.214-3.741.982.998-3.648-.235-.374a9.86 9.86 0 01-1.51-5.26c.001-5.45 4.436-9.884 9.888-9.884 2.64 0 5.122 1.03 6.988 2.898a9.825 9.825 0 012.893 6.994c-.003 5.45-4.437 9.884-9.885 9.884m8.413-18.297A11.815 11.815 0 0012.05 0C5.495 0 .16 5.335.157 11.892c0 2.096.547 4.142 1.588 5.945L.057 24l6.305-1.654a11.882 11.882 0 005.683 1.448h.005c6.554 0 11.89-5.335 11.893-11.893a11.821 11.821 0 00-3.48-8.413z"/>
              </svg>
            </div>
            <div class="contact-btn-content">
              <div class="contact-btn-title">WhatsApp</div>
              <div class="contact-btn-desc">Direct messaging & voice calls</div>
            </div>
          </a>

          <!-- GitHub Discussions Button -->
          <a href="https://github.com/Moongoord/MetaRPC-Gateway-Support" target="_blank" class="contact-btn contact-btn-github">
            <div class="contact-btn-icon">
              <svg width="24" height="24" viewBox="0 0 24 24" fill="currentColor">
                <path d="M12 0c-6.626 0-12 5.373-12 12 0 5.302 3.438 9.8 8.207 11.387.599.111.793-.261.793-.577v-2.234c-3.338.726-4.033-1.416-4.033-1.416-.546-1.387-1.333-1.756-1.333-1.756-1.089-.745.083-.729.083-.729 1.205.084 1.839 1.237 1.839 1.237 1.07 1.834 2.807 1.304 3.492.997.107-.775.418-1.305.762-1.604-2.665-.305-5.467-1.334-5.467-5.931 0-1.311.469-2.381 1.236-3.221-.124-.303-.535-1.524.117-3.176 0 0 1.008-.322 3.301 1.23.957-.266 1.983-.399 3.003-.404 1.02.005 2.047.138 3.006.404 2.291-1.552 3.297-1.23 3.297-1.23.653 1.653.242 2.874.118 3.176.77.84 1.235 1.911 1.235 3.221 0 4.609-2.807 5.624-5.479 5.921.43.372.823 1.102.823 2.222v3.293c0 .319.192.694.801.576 4.765-1.589 8.199-6.086 8.199-11.386 0-6.627-5.373-12-12-12z"/>
              </svg>
            </div>
            <div class="contact-btn-content">
              <div class="contact-btn-title">GitHub Discussions</div>
              <div class="contact-btn-desc">Community support & Q&A</div>
            </div>
          </a>

          <!-- Email Button -->
          <a href="mailto:metarpc.helpers@gmail.com" class="contact-btn contact-btn-email">
            <div class="contact-btn-icon">
              <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <path d="M4 4h16c1.1 0 2 .9 2 2v12c0 1.1-.9 2-2 2H4c-1.1 0-2-.9-2-2V6c0-1.1.9-2 2-2z"></path>
                <polyline points="22,6 12,13 2,6"></polyline>
              </svg>
            </div>
            <div class="contact-btn-content">
              <div class="contact-btn-title">Email</div>
              <div class="contact-btn-desc">Business inquiries & partnerships</div>
            </div>
          </a>
        </div>
      </div>
    </div>

    <!-- Contact Overlay -->
    <div id="contact-overlay" class="progress-overlay"></div>
  `;

  // Insert at end of body
  document.body.insertAdjacentHTML('beforeend', contactHTML);

  // Add event listeners
  document.getElementById('contact-float-btn').addEventListener('click', openContactPanel);
  document.getElementById('contact-panel-close').addEventListener('click', closeContactPanel);
  document.getElementById('contact-overlay').addEventListener('click', closeContactPanel);
}

function openContactPanel() {
  document.getElementById('contact-panel').classList.add('open');
  document.getElementById('contact-overlay').classList.add('visible');
  document.body.style.overflow = 'hidden';
}

function closeContactPanel() {
  document.getElementById('contact-panel').classList.remove('open');
  document.getElementById('contact-overlay').classList.remove('visible');
  document.body.style.overflow = '';
}
