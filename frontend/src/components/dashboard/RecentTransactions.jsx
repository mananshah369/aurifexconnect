import { format } from 'date-fns'
import { motion } from 'framer-motion'
import { FiDownload, FiFilter } from 'react-icons/fi'

function RecentTransactions({ transactions }) {
  const container = {
    hidden: { opacity: 0 },
    show: {
      opacity: 1,
      transition: {
        staggerChildren: 0.1
      }
    }
  }
  
  const item = {
    hidden: { opacity: 0, y: 10 },
    show: { opacity: 1, y: 0 }
  }
  
  return (
    <div className="card overflow-hidden">
      <div className="flex items-center justify-between mb-4">
        <h3 className="text-lg font-semibold text-neutral-900">Recent Transactions</h3>
        <div className="flex space-x-2">
          <button className="btn btn-secondary py-1 px-3 flex items-center text-xs">
            <FiFilter className="h-3 w-3 mr-1" /> Filter
          </button>
          <button className="btn btn-secondary py-1 px-3 flex items-center text-xs">
            <FiDownload className="h-3 w-3 mr-1" /> Export
          </button>
        </div>
      </div>
      
      <motion.div 
        variants={container}
        initial="hidden"
        animate="show"
        className="overflow-hidden"
      >
        <table className="min-w-full divide-y divide-neutral-200">
          <thead>
            <tr>
              <th className="px-4 py-3 text-left text-xs font-medium text-neutral-500 uppercase tracking-wider">Date</th>
              <th className="px-4 py-3 text-left text-xs font-medium text-neutral-500 uppercase tracking-wider">Description</th>
              <th className="px-4 py-3 text-left text-xs font-medium text-neutral-500 uppercase tracking-wider">Category</th>
              <th className="px-4 py-3 text-right text-xs font-medium text-neutral-500 uppercase tracking-wider">Amount</th>
            </tr>
          </thead>
          <tbody className="bg-white divide-y divide-neutral-200">
            {transactions.slice(0, 5).map((transaction) => (
              <motion.tr 
                key={transaction.id}
                variants={item}
                className="hover:bg-neutral-50 cursor-pointer"
              >
                <td className="px-4 py-3 whitespace-nowrap text-sm text-neutral-500">
                  {format(new Date(transaction.date), 'MMM dd, yyyy')}
                </td>
                <td className="px-4 py-3 whitespace-nowrap text-sm font-medium text-neutral-900">
                  {transaction.description}
                </td>
                <td className="px-4 py-3 whitespace-nowrap text-sm text-neutral-500">
                  <span className="capitalize">{transaction.category}</span>
                </td>
                <td className={`px-4 py-3 whitespace-nowrap text-sm font-medium text-right ${
                  transaction.type === 'income' ? 'text-success-600' : 'text-error-600'
                }`}>
                  {transaction.type === 'income' ? '+' : '-'}₹{transaction.amount.toFixed(2)}
                </td>
              </motion.tr>
            ))}
          </tbody>
        </table>
        
        <div className="py-3 px-4 border-t border-neutral-200">
          <a href="/transactions" className="text-sm text-primary-600 hover:text-primary-700 font-medium">
            View all transactions
          </a>
        </div>
      </motion.div>
    </div>
  )
}

export default RecentTransactions