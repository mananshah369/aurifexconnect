import { useAppContext } from '../../context/AppContext'
import { motion } from 'framer-motion'
import { FiMenu, FiBell, FiChevronDown, FiUser } from 'react-icons/fi'
import Dropdown from '../common/Dropdown'
import { useState } from 'react'

function TopBar({ onMenuClick }) {
  const { currentUser } = useAppContext()
  const [showProfileMenu, setShowProfileMenu] = useState(false)
  const [showNotifications, setShowNotifications] = useState(false)

  return (
    <header className="bg-white shadow-sm z-10">
      <div className="px-4 sm:px-6 lg:px-8">
        <div className="flex justify-between h-16">
          <div className="flex">
            <button
              type="button"
              className="md:hidden px-4 text-neutral-500 focus:outline-none focus:ring-2 focus:ring-inset focus:ring-primary-500"
              onClick={onMenuClick}
            >
              <span className="sr-only">Open sidebar</span>
              <FiMenu className="h-6 w-6" />
            </button>
          </div>
          
          <div className="flex items-center">
            {/* Currency selector - simplified */}
            <div className="mr-4">
              <select className="form-select input py-1">
                <option value="INR">₹ INR</option>
                {/* <option value="USD">$ USD</option>
                <option value="GBP">£ GBP</option> */}
              </select>
            </div>
            
            {/* Notifications */}
            <div className="relative">
              <motion.button
                whileTap={{ scale: 0.95 }}
                className="p-2 text-neutral-500 rounded-full hover:bg-neutral-100 focus:outline-none focus:ring-2 focus:ring-primary-500"
                onClick={() => setShowNotifications(!showNotifications)}
              >
                <span className="sr-only">View notifications</span>
                <FiBell className="h-6 w-6" />
                <span className="absolute top-0 right-0 block h-2 w-2 rounded-full bg-primary-600 ring-2 ring-white"></span>
              </motion.button>
              
              {showNotifications && (
                <Dropdown 
                  onClose={() => setShowNotifications(false)}
                  className="w-80 right-0 mt-2"
                >
                  <div className="p-3 border-b border-neutral-200">
                    <h3 className="text-sm font-semibold">Notifications</h3>
                  </div>
                  <div className="max-h-72 overflow-y-auto p-1">
                    <div className="p-3 hover:bg-neutral-50 rounded-lg transition cursor-pointer">
                      <p className="text-sm font-medium text-neutral-900">New invoice paid</p>
                      <p className="text-xs text-neutral-500 mt-1">Invoice #INV-001 from ABC Corp was paid.</p>
                      <p className="text-xs text-neutral-400 mt-1">5m ago</p>
                    </div>
                    <div className="p-3 hover:bg-neutral-50 rounded-lg transition cursor-pointer">
                      <p className="text-sm font-medium text-neutral-900">Budget alert</p>
                      <p className="text-xs text-neutral-500 mt-1">You've reached 80% of your Marketing budget</p>
                      <p className="text-xs text-neutral-400 mt-1">1h ago</p>
                    </div>
                  </div>
                  <div className="p-2 border-t border-neutral-200">
                    <button className="text-sm text-primary-600 hover:text-primary-700 w-full text-center py-1">
                      View all notifications
                    </button>
                  </div>
                </Dropdown>
              )}
            </div>
            
            {/* Profile dropdown */}
            <div className="relative ml-3">
              <div>
                <motion.button
                  whileTap={{ scale: 0.95 }}
                  type="button"
                  className="flex items-center max-w-xs text-sm rounded-full focus:outline-none focus:ring-2 focus:ring-primary-500"
                  onClick={() => setShowProfileMenu(!showProfileMenu)}
                >
                  <span className="sr-only">Open user menu</span>
                  {/* <img
                    className="h-8 w-8 rounded-full object-cover"
                    src={currentUser.avatar}
                    alt=""
                  /> */}
                  <FiUser className="h-8 w-8 rounded-full object-cover text-neutral-500" />
                  <span className="hidden md:flex md:items-center ml-2">
                    <span className="text-sm font-medium text-neutral-700 mr-1">{currentUser.name}</span>
                    <FiChevronDown className="h-4 w-4 text-neutral-400" />
                  </span>
                </motion.button>
              </div>
              
              {showProfileMenu && (
                <Dropdown
                  onClose={() => setShowProfileMenu(false)}
                  className="right-0 mt-2 w-48"
                >
                  <div className="py-1">
                    <a href="#profile" className="block px-4 py-2 text-sm text-neutral-700 hover:bg-neutral-100">
                      Your Profile
                    </a>
                    <a href="#account" className="block px-4 py-2 text-sm text-neutral-700 hover:bg-neutral-100">
                      Account Settings
                    </a>
                    <a href="#support" className="block px-4 py-2 text-sm text-neutral-700 hover:bg-neutral-100">
                      Support
                    </a>
                    <div className="border-t border-neutral-200 my-1"></div>
                    <a href="#signout" className="block px-4 py-2 text-sm text-neutral-700 hover:bg-neutral-100">
                      Sign out
                    </a>
                  </div>
                </Dropdown>
              )}
            </div>
          </div>
        </div>
      </div>
    </header>
  )
}

export default TopBar