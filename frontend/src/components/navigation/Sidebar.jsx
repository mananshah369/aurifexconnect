import { Link, useLocation } from "react-router-dom";
import { motion } from "framer-motion";
import {
  FiX,
  FiHome,
  FiCreditCard,
  FiPieChart,
  FiDollarSign,
  FiFileText,
  FiSettings,
  FiAlignRight,
} from "react-icons/fi";
import { MdInventory } from "react-icons/md";

function Sidebar({ isOpen, onClose, isDesktop = false }) {
  const location = useLocation();

  const navigation = [
    { name: "Dashboard", href: "/", icon: FiHome },
    { name: "Transactions", href: "/transactions", icon: FiCreditCard },
    { name: "Reports", href: "/reports", icon: FiPieChart },
    { name: "Budgets", href: "/budgets", icon: FiAlignRight },
    { name: "Invoices", href: "/invoices", icon: FiFileText },
    { name: "Inventories", href: "/inventories", icon: MdInventory },
    { name: "Settings", href: "/settings", icon: FiSettings },
  ];

  const baseClasses = "h-full flex flex-col bg-primary-800 text-white";
  const mobileClasses = isDesktop
    ? baseClasses
    : `${baseClasses} fixed inset-0 z-40 md:hidden transform ${
        isOpen ? "translate-x-0" : "-translate-x-full"
      } transition-transform duration-300 ease-in-out`;

  return (
    <div className={mobileClasses}>
      {!isDesktop && (
        <div className="absolute top-0 right-0 pt-2 -mr-12">
          <button
            className="ml-1 flex items-center justify-center h-10 w-10 rounded-full focus:outline-none focus:ring-2 focus:ring-inset focus:ring-white"
            onClick={onClose}
          >
            <span className="sr-only">Close sidebar</span>
            <FiX className="h-6 w-6 text-white" />
          </button>
        </div>
      )}

      <div className="flex-1 h-0 overflow-y-auto">
        <div className="flex-shrink-0 flex items-center h-16 px-6">
          <Link to="/" className="text-white text-2xl font-bold tracking-tight">
            <span className="text-primary-300">AuriFex</span>Connect
          </Link>
        </div>

        <nav className="mt-8 flex-1 px-4 space-y-1">
          {navigation.map((item) => {
            const isActive = location.pathname === item.href;

            return (
              <Link
                key={item.name}
                to={item.href}
                className={`group flex items-center px-4 py-3 text-sm font-medium rounded-lg transition-colors ${
                  isActive
                    ? "bg-primary-700 text-white"
                    : "text-primary-100 hover:bg-primary-700"
                }`}
              >
                <motion.div
                  whileHover={{ scale: 1.1 }}
                  whileTap={{ scale: 0.95 }}
                  className="mr-3"
                >
                  <item.icon
                    className={`h-5 w-5 ${
                      isActive ? "text-white" : "text-primary-300"
                    }`}
                  />
                </motion.div>
                {item.name}
              </Link>
            );
          })}
        </nav>
      </div>

      {/* App upgrade notice */}
      {/* <div className="p-4">
        <div className="bg-primary-700 p-4 rounded-lg">
          <h4 className="text-sm font-semibold text-white mb-1">Pro Features</h4>
          <p className="text-xs text-primary-200 mb-3">Unlock advanced reporting, forecasting and more.</p>
          <button className="bg-white text-primary-800 text-xs font-semibold px-3 py-1.5 rounded hover:bg-primary-50 transition-colors w-full">
            Upgrade Now
          </button>
        </div>
      </div> */}
    </div>
  );
}

export default Sidebar;
