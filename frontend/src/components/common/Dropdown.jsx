import { useEffect, useRef } from 'react'
import { motion } from 'framer-motion'

function Dropdown({ children, onClose, className = '' }) {
  const dropdownRef = useRef(null)
  
  useEffect(() => {
    function handleClickOutside(event) {
      if (dropdownRef.current && !dropdownRef.current.contains(event.target)) {
        onClose()
      }
    }
    
    document.addEventListener('mousedown', handleClickOutside)
    return () => {
      document.removeEventListener('mousedown', handleClickOutside)
    }
  }, [onClose])
  
  return (
    <motion.div
      initial={{ opacity: 0, y: -5 }}
      animate={{ opacity: 1, y: 0 }}
      exit={{ opacity: 0, y: -5 }}
      transition={{ duration: 0.2 }}
      className={`origin-top-right absolute z-50 rounded-lg shadow-lg bg-white ring-1 ring-black ring-opacity-5 ${className}`}
      ref={dropdownRef}
    >
      {children}
    </motion.div>
  )
}

export default Dropdown