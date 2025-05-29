import { useState } from 'react'
import { motion } from 'framer-motion'
import { useAppContext } from '../context/AppContext'
import { FiCreditCard, FiUser, FiLock, FiGlobe, FiBell, FiSliders, FiGrid } from 'react-icons/fi'

function Settings() {
  const { currentUser, setCurrency } = useAppContext()
  const [activeTab, setActiveTab] = useState('profile')
  
  // Example form states
  const [profileForm, setProfileForm] = useState({
    name: currentUser.name,
    email: currentUser.email,
    phone: '(555) 123-4567',
    company: 'Example Corp',
    country: 'United States',
  })
  
  const [currencySettings, setCurrencySettings] = useState({
    currency: 'USD',
    locale: 'en-US',
    dateFormat: 'MM/DD/YYYY',
  })
  
  const handleCurrencyChange = (e) => {
    setCurrencySettings({
      ...currencySettings,
      currency: e.target.value,
    })
    setCurrency(e.target.value)
  }
  
  const handleProfileChange = (e) => {
    setProfileForm({
      ...profileForm,
      [e.target.name]: e.target.value,
    })
  }
  
  const tabs = [
    { id: 'profile', label: 'Profile', icon: FiUser },
    { id: 'account', label: 'Account', icon: FiCreditCard },
    { id: 'security', label: 'Security', icon: FiLock },
    { id: 'preferences', label: 'Preferences', icon: FiSliders },
    { id: 'notifications', label: 'Notifications', icon: FiBell },
    { id: 'integrations', label: 'Integrations', icon: FiGrid },
    { id: 'localization', label: 'Localization', icon: FiGlobe },
  ]
  
  return (
    <motion.div
      initial={{ opacity: 0, y: 20 }}
      animate={{ opacity: 1, y: 0 }}
      transition={{ duration: 0.5 }}
    >
      <div className="md:flex md:items-center md:justify-between mb-6">
        <div className="flex-1 min-w-0">
          <h1 className="text-2xl font-bold leading-7 text-neutral-900 sm:text-3xl sm:leading-9 sm:truncate">
            Settings
          </h1>
          <p className="mt-1 text-sm text-neutral-500">
            Manage your account settings and preferences
          </p>
        </div>
      </div>
      
      <div className="grid grid-cols-1 md:grid-cols-4 gap-6">
        {/* Tabs */}
        <div className="md:col-span-1">
          <div className="card">
            <nav className="space-y-1">
              {tabs.map((tab) => (
                <button
                  key={tab.id}
                  className={`flex items-center px-3 py-2 text-sm font-medium rounded-md w-full ${
                    activeTab === tab.id
                      ? 'bg-primary-50 text-primary-700'
                      : 'text-neutral-700 hover:bg-neutral-50'
                  }`}
                  onClick={() => setActiveTab(tab.id)}
                >
                  <tab.icon className={`mr-3 h-5 w-5 ${
                    activeTab === tab.id ? 'text-primary-500' : 'text-neutral-400'
                  }`} />
                  {tab.label}
                </button>
              ))}
            </nav>
          </div>
        </div>
        
        {/* Content */}
        <div className="md:col-span-3">
          <div className="card">
            {activeTab === 'profile' && (
              <motion.div
                initial={{ opacity: 0 }}
                animate={{ opacity: 1 }}
                transition={{ duration: 0.3 }}
              >
                <h2 className="text-lg font-medium text-neutral-900 mb-6">Profile Information</h2>
                
                <div className="flex items-center mb-6">
                  <img
                    className="h-16 w-16 rounded-full object-cover"
                    src={currentUser.avatar}
                    alt="User avatar"
                  />
                  <div className="ml-4">
                    <button className="btn btn-secondary text-sm">Change Avatar</button>
                    <p className="mt-1 text-xs text-neutral-500">JPG, GIF or PNG. 1MB max.</p>
                  </div>
                </div>
                
                <div className="space-y-4">
                  <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                    <div>
                      <label htmlFor="name" className="block text-sm font-medium text-neutral-700 mb-1">
                        Full Name
                      </label>
                      <input
                        type="text"
                        id="name"
                        name="name"
                        className="input"
                        value={profileForm.name}
                        onChange={handleProfileChange}
                      />
                    </div>
                    <div>
                      <label htmlFor="email" className="block text-sm font-medium text-neutral-700 mb-1">
                        Email Address
                      </label>
                      <input
                        type="email"
                        id="email"
                        name="email"
                        className="input"
                        value={profileForm.email}
                        onChange={handleProfileChange}
                      />
                    </div>
                  </div>
                  
                  <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                    <div>
                      <label htmlFor="phone" className="block text-sm font-medium text-neutral-700 mb-1">
                        Phone Number
                      </label>
                      <input
                        type="text"
                        id="phone"
                        name="phone"
                        className="input"
                        value={profileForm.phone}
                        onChange={handleProfileChange}
                      />
                    </div>
                    <div>
                      <label htmlFor="company" className="block text-sm font-medium text-neutral-700 mb-1">
                        Company
                      </label>
                      <input
                        type="text"
                        id="company"
                        name="company"
                        className="input"
                        value={profileForm.company}
                        onChange={handleProfileChange}
                      />
                    </div>
                  </div>
                  
                  <div>
                    <label htmlFor="country" className="block text-sm font-medium text-neutral-700 mb-1">
                      Country
                    </label>
                    <select
                      id="country"
                      name="country"
                      className="input"
                      value={profileForm.country}
                      onChange={handleProfileChange}
                    >
                      <option>United States</option>
                      <option>Canada</option>
                      <option>United Kingdom</option>
                      <option>Australia</option>
                      <option>Germany</option>
                      <option>France</option>
                      <option>Japan</option>
                    </select>
                  </div>
                </div>
                
                <div className="mt-6 flex justify-end">
                  <button className="btn btn-secondary mr-3">
                    Cancel
                  </button>
                  <button className="btn btn-primary">
                    Save Changes
                  </button>
                </div>
              </motion.div>
            )}
            
            {activeTab === 'localization' && (
              <motion.div
                initial={{ opacity: 0 }}
                animate={{ opacity: 1 }}
                transition={{ duration: 0.3 }}
              >
                <h2 className="text-lg font-medium text-neutral-900 mb-6">Localization Settings</h2>
                
                <div className="space-y-4">
                  <div>
                    <label htmlFor="currency" className="block text-sm font-medium text-neutral-700 mb-1">
                      Currency
                    </label>
                    <select
                      id="currency"
                      name="currency"
                      className="input"
                      value={currencySettings.currency}
                      onChange={handleCurrencyChange}
                    >
                      <option value="USD">US Dollar (USD)</option>
                      <option value="EUR">Euro (EUR)</option>
                      <option value="GBP">British Pound (GBP)</option>
                      <option value="CAD">Canadian Dollar (CAD)</option>
                      <option value="AUD">Australian Dollar (AUD)</option>
                      <option value="JPY">Japanese Yen (JPY)</option>
                    </select>
                  </div>
                  
                  <div>
                    <label htmlFor="locale" className="block text-sm font-medium text-neutral-700 mb-1">
                      Language & Region
                    </label>
                    <select
                      id="locale"
                      name="locale"
                      className="input"
                      value={currencySettings.locale}
                      onChange={(e) => setCurrencySettings({...currencySettings, locale: e.target.value})}
                    >
                      <option value="en-US">English (United States)</option>
                      <option value="en-GB">English (United Kingdom)</option>
                      <option value="fr-FR">French (France)</option>
                      <option value="de-DE">German (Germany)</option>
                      <option value="es-ES">Spanish (Spain)</option>
                      <option value="ja-JP">Japanese (Japan)</option>
                    </select>
                  </div>
                  
                  <div>
                    <label htmlFor="dateFormat" className="block text-sm font-medium text-neutral-700 mb-1">
                      Date Format
                    </label>
                    <select
                      id="dateFormat"
                      name="dateFormat"
                      className="input"
                      value={currencySettings.dateFormat}
                      onChange={(e) => setCurrencySettings({...currencySettings, dateFormat: e.target.value})}
                    >
                      <option value="MM/DD/YYYY">MM/DD/YYYY</option>
                      <option value="DD/MM/YYYY">DD/MM/YYYY</option>
                      <option value="YYYY-MM-DD">YYYY-MM-DD</option>
                      <option value="MMM DD, YYYY">MMM DD, YYYY</option>
                      <option value="DD MMM YYYY">DD MMM YYYY</option>
                    </select>
                  </div>
                </div>
                
                <div className="mt-6 flex justify-end">
                  <button className="btn btn-secondary mr-3">
                    Cancel
                  </button>
                  <button className="btn btn-primary">
                    Save Changes
                  </button>
                </div>
              </motion.div>
            )}
            
            {activeTab !== 'profile' && activeTab !== 'localization' && (
              <div className="py-6 px-4 text-center">
                <FiSliders className="mx-auto h-12 w-12 text-neutral-400" />
                <h3 className="mt-2 text-sm font-medium text-neutral-900">
                  {tabs.find(tab => tab.id === activeTab)?.label} Settings
                </h3>
                <p className="mt-1 text-sm text-neutral-500">
                  This section is under development.
                </p>
                <div className="mt-6">
                  <button className="btn btn-primary">Configure</button>
                </div>
              </div>
            )}
          </div>
        </div>
      </div>
    </motion.div>
  )
}

export default Settings