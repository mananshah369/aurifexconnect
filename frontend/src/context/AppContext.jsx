import { createContext, useContext, useState, useEffect } from 'react'
import { mockTransactions, mockAccounts, mockCategories } from '../data/mockData'

const AppContext = createContext()

export function useAppContext() {
  return useContext(AppContext)
}

export function AppProvider({ children }) {
  const [transactions, setTransactions] = useState([])
  const [accounts, setAccounts] = useState([])
  const [categories, setCategories] = useState([])
  const [currentUser, setCurrentUser] = useState({
    id: 1,
    name: 'User',
    email: 'john@example.com',
    avatar: '/Users/mananshah/Downloads/project/src/assets/user-286.svg'
  })
  const [isLoading, setIsLoading] = useState(true)
  const [currency, setCurrency] = useState('INR')
  
  useEffect(() => {
    // Simulate data loading
    const loadData = async () => {
      setIsLoading(true)
      
      // Simulate API call delay
      await new Promise(resolve => setTimeout(resolve, 800))
      
      setTransactions(mockTransactions)
      setAccounts(mockAccounts)
      setCategories(mockCategories)
      
      setIsLoading(false)
    }
    
    loadData()
  }, [])
  
  // Calculate total income
  const totalIncome = transactions
    .filter(transaction => transaction.type === 'income')
    .reduce((sum, transaction) => sum + transaction.amount, 0)
  
  // Calculate total expenses
  const totalExpenses = transactions
    .filter(transaction => transaction.type === 'expense')
    .reduce((sum, transaction) => sum + transaction.amount, 0)
  
  // Calculate balance
  const balance = totalIncome - totalExpenses
  
  const addTransaction = (newTransaction) => {
    setTransactions(prev => [...prev, { 
      id: Date.now().toString(),
      date: new Date().toISOString(),
      ...newTransaction
    }])
  }
  
  const updateTransaction = (id, updatedTransaction) => {
    setTransactions(prev => 
      prev.map(transaction => 
        transaction.id === id ? { ...transaction, ...updatedTransaction } : transaction
      )
    )
  }
  
  const deleteTransaction = (id) => {
    setTransactions(prev => prev.filter(transaction => transaction.id !== id))
  }
  
  const value = {
    transactions,
    accounts,
    categories,
    currentUser,
    isLoading,
    currency,
    totalIncome,
    totalExpenses,
    balance,
    addTransaction,
    updateTransaction,
    deleteTransaction,
    setCurrency,
  }
  
  return (
    <AppContext.Provider value={value}>
      {children}
    </AppContext.Provider>
  )
}