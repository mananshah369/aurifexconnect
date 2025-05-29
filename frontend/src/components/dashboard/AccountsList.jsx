import { motion } from 'framer-motion'
import { FiArrowRight } from 'react-icons/fi'

function AccountsList({ accounts }) {
  const formatCurrency = (value, currency) => {
    return new Intl.NumberFormat('en-US', {
      style: 'currency',
      currency: currency
    }).format(value)
  }
  
  const getAccountIcon = (type) => {
    switch (type) {
      case 'checking':
        return '💳'
      case 'savings':
        return '💰'
      case 'credit':
        return '💸'
      case 'investment':
        return '📈'
      default:
        return '🏦'
    }
  }
  
  return (
    <div className="card">
      <div className="flex items-center justify-between mb-4">
        <h3 className="text-lg font-semibold text-neutral-900">My Accounts</h3>
        <button className="text-sm text-primary-600 hover:text-primary-700 font-medium flex items-center">
          Manage <FiArrowRight className="ml-1 h-4 w-4" />
        </button>
      </div>
      
      <div className="space-y-3">
        {accounts.map((account) => (
          <motion.div
            key={account.id}
            whileHover={{ x: 4 }}
            className="p-3 rounded-lg border border-neutral-200 hover:border-primary-300 transition-colors cursor-pointer"
          >
            <div className="flex items-center justify-between">
              <div className="flex items-center">
                <span className="text-xl mr-3">{getAccountIcon(account.type)}</span>
                <div>
                  <p className="text-sm font-medium text-neutral-900">{account.name}</p>
                  <p className="text-xs text-neutral-500">{account.institution}</p>
                </div>
              </div>
              <div className="text-right">
                <p className={`text-sm font-semibold ${account.balance < 0 ? 'text-error-600' : 'text-neutral-900'}`}>
                  {formatCurrency(account.balance, account.currency)}
                </p>
                <p className="text-xs text-neutral-500 capitalize">{account.type} Account</p>
              </div>
            </div>
          </motion.div>
        ))}
      </div>
      
      <div className="mt-4">
        <button className="btn btn-secondary w-full">
          + Add Account
        </button>
      </div>
    </div>
  )
}

export default AccountsList