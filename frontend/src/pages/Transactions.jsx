import { useState } from 'react'
import { useAppContext } from '../context/AppContext'
import { motion } from 'framer-motion'
import { FiFilter, FiDownload, FiPlusCircle, FiChevronDown, FiSearch } from 'react-icons/fi'
import { format } from 'date-fns'

function Transactions() {
  const { transactions, categories, accounts, addTransaction } = useAppContext()
  const [filterOpen, setFilterOpen] = useState(false)
  const [searchTerm, setSearchTerm] = useState('')
  
  // Filter states
  const [typeFilter, setTypeFilter] = useState('all')
  const [categoryFilter, setCategoryFilter] = useState('all')
  const [accountFilter, setAccountFilter] = useState('all')
  const [dateFilter, setDateFilter] = useState('all')
  
  // Apply filters
  const filteredTransactions = transactions.filter(transaction => {
    // Search term
    if (searchTerm && !transaction.description.toLowerCase().includes(searchTerm.toLowerCase())) {
      return false
    }
    
    // Type filter
    if (typeFilter !== 'all' && transaction.type !== typeFilter) {
      return false
    }
    
    // Category filter
    if (categoryFilter !== 'all' && transaction.category !== categoryFilter) {
      return false
    }
    
    // Account filter
    if (accountFilter !== 'all' && transaction.account !== accountFilter) {
      return false
    }
    
    // Date filter (simplified)
    if (dateFilter === 'today') {
      const today = new Date().toISOString().split('T')[0]
      const transactionDate = new Date(transaction.date).toISOString().split('T')[0]
      if (today !== transactionDate) {
        return false
      }
    } else if (dateFilter === 'week') {
      const weekAgo = new Date()
      weekAgo.setDate(weekAgo.getDate() - 7)
      if (new Date(transaction.date) < weekAgo) {
        return false
      }
    } else if (dateFilter === 'month') {
      const monthAgo = new Date()
      monthAgo.setMonth(monthAgo.getMonth() - 1)
      if (new Date(transaction.date) < monthAgo) {
        return false
      }
    }
    
    return true
  })
  
  // Add new transaction modal state
  const [showAddModal, setShowAddModal] = useState(false)
  const [newTransaction, setNewTransaction] = useState({
    description: '',
    amount: '',
    type: 'expense',
    category: '',
    account: '',
  })
  
  const handleAddTransaction = () => {
    //setShowAddModal(true);
    console.log('Transaction added:');
    if (
      newTransaction.description && 
      newTransaction.amount && 
      newTransaction.category && 
      newTransaction.account
    ) {
      addTransaction({
        ...newTransaction,
        amount: parseFloat(newTransaction.amount),
        status: 'completed'
      })
      
      setNewTransaction({
        description: '',
        amount: '',
        type: 'expense',
        category: '',
        account: '',
      })

      
    }
    else {
      alert('Please fill in all fields.')
    }
  }
  
  const pageVariants = {
    initial: { opacity: 0, y: 20 },
    animate: { opacity: 1, y: 0 }
  }
  
  console.log("Hi");
  console.log(showAddModal);
  return (
    <motion.div
      variants={pageVariants}
      initial="initial"
      animate="animate"
      transition={{ duration: 0.5 }}
    >
      <div className="md:flex md:items-center md:justify-between mb-6">
        <div className="flex-1 min-w-0">
          <h1 className="text-2xl font-bold leading-7 text-neutral-900 sm:text-3xl sm:leading-9 sm:truncate">
            Transactions
          </h1>
        </div>
        <div className="mt-4 flex md:mt-0 md:ml-4">
          <button 
            className="btn btn-secondary mr-3 flex items-center"
            onClick={() => setFilterOpen(!filterOpen)}
          >
            <FiFilter className="mr-2 h-4 w-4" />
            Filter
            <FiChevronDown className={`ml-2 h-4 w-4 transition-transform ${filterOpen ? 'rotate-180' : ''}`} />
          </button>
          <button className="btn btn-secondary mr-3">
            <FiDownload className="mr-2 h-4 w-4" />
            Export
          </button>
          <button 
            className="btn btn-primary flex items-center"
            onClick={() => setShowAddModal(true)}
          >
            <FiPlusCircle className="mr-2 h-4 w-4" />
            Add Transaction
          </button>
        </div>
      </div>
      
      {/* Search and filters */}
      <div className="mb-6">
        <div className="relative">
          <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
            <FiSearch className="h-5 w-5 text-neutral-400" />
          </div>
          <input
            type="text"
            className="input pl-10"
            placeholder="Search transactions..."
            value={searchTerm}
            onChange={e => setSearchTerm(e.target.value)}
          />
        </div>
        
        {filterOpen && (
          <motion.div 
            initial={{ opacity: 0, y: -10 }}
            animate={{ opacity: 1, y: 0 }}
            className="mt-4 grid grid-cols-1 md:grid-cols-4 gap-4"
          >
            <div>
              <label className="block text-sm font-medium text-neutral-700 mb-1">Type</label>
              <select 
                className="input"
                value={typeFilter}
                onChange={e => setTypeFilter(e.target.value)}
              >
                <option value="all">All Types</option>
                <option value="income">Income</option>
                <option value="expense">Expense</option>
              </select>
            </div>
            <div>
              <label className="block text-sm font-medium text-neutral-700 mb-1">Category</label>
              <select 
                className="input"
                value={categoryFilter}
                onChange={e => setCategoryFilter(e.target.value)}
              >
                <option value="all">All Categories</option>
                {categories.map(category => (
                  <option key={category.id} value={category.name.toLowerCase()}>
                    {category.name}
                  </option>
                ))}
              </select>
            </div>
            <div>
              <label className="block text-sm font-medium text-neutral-700 mb-1">Account</label>
              <select 
                className="input"
                value={accountFilter}
                onChange={e => setAccountFilter(e.target.value)}
              >
                <option value="all">All Accounts</option>
                {accounts.map(account => (
                  <option key={account.id} value={account.name}>
                    {account.name}
                  </option>
                ))}
              </select>
            </div>
            <div>
              <label className="block text-sm font-medium text-neutral-700 mb-1">Date</label>
              <select 
                className="input"
                value={dateFilter}
                onChange={e => setDateFilter(e.target.value)}
              >
                <option value="all">All Time</option>
                <option value="today">Today</option>
                <option value="week">Last 7 Days</option>
                <option value="month">Last 30 Days</option>
              </select>
            </div>
          </motion.div>
        )}
      </div>
      
      {/* Transactions table */}
      <div className="card overflow-hidden">
        <div className="overflow-x-auto">
          <table className="min-w-full divide-y divide-neutral-200">
            <thead className="bg-neutral-50">
              <tr>
                <th className="px-6 py-3 text-left text-xs font-medium text-neutral-500 uppercase tracking-wider">
                  Date
                </th>
                <th className="px-6 py-3 text-left text-xs font-medium text-neutral-500 uppercase tracking-wider">
                  Description
                </th>
                <th className="px-6 py-3 text-left text-xs font-medium text-neutral-500 uppercase tracking-wider">
                  Category
                </th>
                <th className="px-6 py-3 text-left text-xs font-medium text-neutral-500 uppercase tracking-wider">
                  Account
                </th>
                <th className="px-6 py-3 text-right text-xs font-medium text-neutral-500 uppercase tracking-wider">
                  Amount
                </th>
                <th className="px-6 py-3 text-right text-xs font-medium text-neutral-500 uppercase tracking-wider">
                  Status
                </th>
              </tr>
            </thead>
            <tbody className="bg-white divide-y divide-neutral-200">
              {filteredTransactions.map((transaction) => (
                <motion.tr 
                  key={transaction.id}
                  whileHover={{ backgroundColor: '#f9fafb' }}
                  className="cursor-pointer"
                >
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-neutral-500">
                    {format(new Date(transaction.date), 'MMM dd, yyyy')}
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-neutral-900">
                    {transaction.description}
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-neutral-500 capitalize">
                    {transaction.category}
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-neutral-500">
                    {transaction.account}
                  </td>
                  <td className={`px-6 py-4 whitespace-nowrap text-sm font-medium text-right ${
                    transaction.type === 'income' ? 'text-success-600' : 'text-error-600'
                  }`}>
                    {transaction.type === 'income' ? '+' : '-'}₹{transaction.amount.toFixed(2)}
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap text-right text-sm">
                    <span className={`badge ${
                      transaction.status === 'completed' 
                        ? 'badge-success' 
                        : transaction.status === 'pending' 
                          ? 'badge-warning'
                          : 'bg-neutral-100 text-neutral-800'
                    }`}>
                      {transaction.status}
                    </span>
                  </td>
                </motion.tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
      
      {/* Add Transaction Modal */}
      {showAddModal && (
        <div className="fixed inset-0 z-50 overflow-y-auto">
          <div className="flex items-end justify-center min-h-screen pt-4 px-4 pb-20 text-center sm:block sm:p-0">
            <div className="fixed inset-0 transition-opacity" aria-hidden="true">
              <div className="absolute inset-0 bg-neutral-500 opacity-75"></div>
            </div>
            
            <motion.div 
              initial={{ opacity: 0, scale: 0.95, y: 20 }}
              animate={{ opacity: 1, scale: 1, y: 0 }}
              className="inline-block align-bottom bg-white rounded-lg text-left overflow-hidden shadow-xl transform transition-all sm:my-8 sm:align-middle sm:max-w-lg sm:w-full"
            >
              <div className="bg-white px-4 pt-5 pb-4 sm:p-6 sm:pb-4">
                <div className="sm:flex sm:items-start">
                  <div className="mt-3 text-center sm:mt-0 sm:ml-4 sm:text-left w-full">
                    <h3 className="text-lg leading-6 font-medium text-neutral-900 mb-4">
                      Add New Transaction
                    </h3>
                    
                    <div className="mt-4 space-y-4">
                      <div>
                        <label className="block text-sm font-medium text-neutral-700 mb-1">
                          Description
                        </label>
                        <input
                          type="text"
                          className="input"
                          value={newTransaction.description}
                          onChange={e => setNewTransaction({...newTransaction, description: e.target.value})}
                        />
                      </div>
                      
                      <div>
                        <label className="block text-sm font-medium text-neutral-700 mb-1">
                          Amount
                        </label>
                        <input
                          type="number"
                          step="0.01"
                          className="input"
                          value={newTransaction.amount}
                          onChange={e => setNewTransaction({...newTransaction, amount: e.target.value})}
                        />
                      </div>
                      
                      <div>
                        <label className="block text-sm font-medium text-neutral-700 mb-1">
                          Type
                        </label>
                        <div className="flex space-x-4">
                          <label className="inline-flex items-center">
                            <input
                              type="radio"
                              className="form-radio text-primary-600"
                              name="transactionType"
                              value="expense"
                              checked={newTransaction.type === 'expense'}
                              onChange={() => setNewTransaction({...newTransaction, type: 'expense'})}
                            />
                            <span className="ml-2 text-sm text-neutral-700">Expense</span>
                          </label>
                          <label className="inline-flex items-center">
                            <input
                              type="radio"
                              className="form-radio text-primary-600"
                              name="transactionType"
                              value="income"
                              checked={newTransaction.type === 'income'}
                              onChange={() => setNewTransaction({...newTransaction, type: 'income'})}
                            />
                            <span className="ml-2 text-sm text-neutral-700">Income</span>
                          </label>
                        </div>
                      </div>
                      
                      <div>
                        <label className="block text-sm font-medium text-neutral-700 mb-1">
                          Category
                        </label>
                        <select
                          className="input"
                          value={newTransaction.category}
                          onChange={e => setNewTransaction({...newTransaction, category: e.target.value})}
                        >
                          <option value="">Select a category</option>
                          {categories
                            .filter(cat => cat.type === newTransaction.type)
                            .map(category => (
                              <option key={category.id} value={category.name.toLowerCase()}>
                                {category.name}
                              </option>
                            ))
                          }
                        </select>
                      </div>
                      
                      <div>
                        <label className="block text-sm font-medium text-neutral-700 mb-1">
                          Account
                        </label>
                        <select
                          className="input"
                          value={newTransaction.account}
                          onChange={e => setNewTransaction({...newTransaction, account: e.target.value})}
                        >
                          <option value="">Select an account</option>
                          {accounts.map(account => (
                            <option key={account.id} value={account.name}>
                              {account.name}
                            </option>
                          ))}
                        </select>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              
              <div className="bg-neutral-50 px-4 py-3 sm:px-6 sm:flex sm:flex-row-reverse">
                <button
                  type="button"
                  className="btn btn-primary w-full sm:w-auto sm:ml-3"
                  onClick={handleAddTransaction}
                >
                  Add Transaction
                </button>
                <button
                  type="button"
                  className="btn btn-secondary w-full sm:w-auto mt-3 sm:mt-0"
                  onClick={() => setShowAddModal(false)}
                >
                  Cancel
                </button>
              </div>
            </motion.div>
          </div>
        </div>
      )}
    </motion.div>
  )
}

export default Transactions