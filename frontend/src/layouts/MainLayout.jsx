import { useState, useEffect } from 'react'
import { Outlet, useLocation } from 'react-router-dom'
import Sidebar from '../components/navigation/Sidebar'
import TopBar from '../components/navigation/TopBar'
import { useAppContext } from '../context/AppContext'
import LoadingScreen from '../components/common/LoadingScreen'

function MainLayout() {
  const [sidebarOpen, setSidebarOpen] = useState(false)
  const { isLoading } = useAppContext()
  const location = useLocation()

  useEffect(() => {
    // Close sidebar when location changes on mobile
    setSidebarOpen(false)
  }, [location])

  if (isLoading) {
    return <LoadingScreen />
  }

  return (
    <div className="h-screen flex overflow-hidden bg-neutral-50">
      {/* Mobile sidebar */}
      <Sidebar isOpen={sidebarOpen} onClose={() => setSidebarOpen(false)} />
      
      {/* Static sidebar for desktop */}
      <div className="hidden md:flex md:flex-shrink-0">
        <div className="flex flex-col w-64">
          <Sidebar isDesktop={true} />
        </div>
      </div>
      
      {/* Main content */}
      <div className="flex flex-col w-0 flex-1 overflow-hidden">
        <TopBar onMenuClick={() => setSidebarOpen(true)} />
        
        <main className="flex-1 relative overflow-y-auto focus:outline-none">
          <div className="py-6 px-4 sm:px-6 lg:px-8">
            <Outlet />
          </div>
        </main>
      </div>
    </div>
  )
}

export default MainLayout